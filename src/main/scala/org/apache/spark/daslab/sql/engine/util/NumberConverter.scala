package org.apache.spark.daslab.sql.engine.util



//todo spark core
import org.apache.spark.unsafe.types.UTF8String

object NumberConverter {

  /**
   * Divide x by m as if x is an unsigned 64-bit integer. Examples:
   * unsignedLongDiv(-1, 2) == Long.MAX_VALUE unsignedLongDiv(6, 3) == 2
   * unsignedLongDiv(0, 5) == 0
   *
   * @param x is treated as unsigned
   * @param m is treated as signed
   */
  private def unsignedLongDiv(x: Long, m: Int): Long = {
    if (x >= 0) {
      x / m
    } else {
      // Let uval be the value of the unsigned long with the same bits as x
      // Two's complement => x = uval - 2*MAX - 2
      // => uval = x + 2*MAX + 2
      // Now, use the fact: (a+b)/c = a/c + b/c + (a%c+b%c)/c
      x / m + 2 * (Long.MaxValue / m) + 2 / m + (x % m + 2 * (Long.MaxValue % m) + 2 % m) / m
    }
  }

  /**
   * Decode v into value[].
   *
   * @param v is treated as an unsigned 64-bit integer
   * @param radix must be between MIN_RADIX and MAX_RADIX
   */
  private def decode(v: Long, radix: Int, value: Array[Byte]): Unit = {
    var tmpV = v
    java.util.Arrays.fill(value, 0.asInstanceOf[Byte])
    var i = value.length - 1
    while (tmpV != 0) {
      val q = unsignedLongDiv(tmpV, radix)
      value(i) = (tmpV - q * radix).asInstanceOf[Byte]
      tmpV = q
      i -= 1
    }
  }

  /**
   * Convert value[] into a long. On overflow, return -1 (as mySQL does). If a
   * negative digit is found, ignore the suffix starting there.
   *
   * @param radix  must be between MIN_RADIX and MAX_RADIX
   * @param fromPos is the first element that should be considered
   * @return the result should be treated as an unsigned 64-bit integer.
   */
  private def encode(radix: Int, fromPos: Int, value: Array[Byte]): Long = {
    var v: Long = 0L
    val bound = unsignedLongDiv(-1 - radix, radix) // Possible overflow once
    var i = fromPos
    while (i < value.length && value(i) >= 0) {
      if (v >= bound) {
        // Check for overflow
        if (unsignedLongDiv(-1 - value(i), radix) < v) {
          return -1
        }
      }
      v = v * radix + value(i)
      i += 1
    }
    v
  }

  /**
   * Convert the bytes in value[] to the corresponding chars.
   *
   * @param radix must be between MIN_RADIX and MAX_RADIX
   * @param fromPos is the first nonzero element
   */
  private def byte2char(radix: Int, fromPos: Int, value: Array[Byte]): Unit = {
    var i = fromPos
    while (i < value.length) {
      value(i) = Character.toUpperCase(Character.forDigit(value(i), radix)).asInstanceOf[Byte]
      i += 1
    }
  }

  /**
   * Convert the chars in value[] to the corresponding integers. Convert invalid
   * characters to -1.
   *
   * @param radix must be between MIN_RADIX and MAX_RADIX
   * @param fromPos is the first nonzero element
   */
  private def char2byte(radix: Int, fromPos: Int, value: Array[Byte]): Unit = {
    var i = fromPos
    while (i < value.length) {
      value(i) = Character.digit(value(i), radix).asInstanceOf[Byte]
      i += 1
    }
  }

  /**
   * Convert numbers between different number bases. If toBase>0 the result is
   * unsigned, otherwise it is signed.
   * NB: This logic is borrowed from org.apache.hadoop.hive.ql.ud.UDFConv
   */
  def convert(n: Array[Byte], fromBase: Int, toBase: Int ): UTF8String = {
    if (fromBase < Character.MIN_RADIX || fromBase > Character.MAX_RADIX
      || Math.abs(toBase) < Character.MIN_RADIX
      || Math.abs(toBase) > Character.MAX_RADIX) {
      return null
    }

    if (n.length == 0) {
      return null
    }

    var (negative, first) = if (n(0) == '-') (true, 1) else (false, 0)

    // Copy the digits in the right side of the array
    val temp = new Array[Byte](64)
    var i = 1
    while (i <= n.length - first) {
      temp(temp.length - i) = n(n.length - i)
      i += 1
    }
    char2byte(fromBase, temp.length - n.length + first, temp)

    // Do the conversion by going through a 64 bit integer
    var v = encode(fromBase, temp.length - n.length + first, temp)
    if (negative && toBase > 0) {
      if (v < 0) {
        v = -1
      } else {
        v = -v
      }
    }
    if (toBase < 0 && v < 0) {
      v = -v
      negative = true
    }
    decode(v, Math.abs(toBase), temp)

    // Find the first non-zero digit or the last digits if all are zero.
    val firstNonZeroPos = {
      val firstNonZero = temp.indexWhere( _ != 0)
      if (firstNonZero != -1) firstNonZero else temp.length - 1
    }
    byte2char(Math.abs(toBase), firstNonZeroPos, temp)

    var resultStartPos = firstNonZeroPos
    if (negative && toBase < 0) {
      resultStartPos = firstNonZeroPos - 1
      temp(resultStartPos) = '-'
    }
    UTF8String.fromBytes(java.util.Arrays.copyOfRange(temp, resultStartPos, temp.length))
  }
}
