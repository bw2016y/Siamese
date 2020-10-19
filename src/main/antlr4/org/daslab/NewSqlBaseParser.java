// Generated from C:/Users/admin/IdeaProjects/Siamese/src/main/antlr4/org/daslab\NewSqlBase.g4 by ANTLR 4.8
package org.daslab;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NewSqlBaseParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, SELECT=11, FROM=12, ADD=13, AS=14, ALL=15, ANY=16, DISTINCT=17, 
		WHERE=18, GROUP=19, BY=20, GROUPING=21, SETS=22, CUBE=23, ROLLUP=24, ORDER=25, 
		HAVING=26, LIMIT=27, AT=28, OR=29, AND=30, IN=31, NOT=32, NO=33, EXISTS=34, 
		BETWEEN=35, LIKE=36, RLIKE=37, IS=38, NULL=39, TRUE=40, FALSE=41, NULLS=42, 
		ASC=43, DESC=44, FOR=45, INTERVAL=46, CASE=47, WHEN=48, THEN=49, ELSE=50, 
		END=51, JOIN=52, CROSS=53, OUTER=54, INNER=55, LEFT=56, SEMI=57, RIGHT=58, 
		FULL=59, NATURAL=60, ON=61, PIVOT=62, LATERAL=63, WINDOW=64, OVER=65, 
		PARTITION=66, RANGE=67, ROWS=68, UNBOUNDED=69, PRECEDING=70, FOLLOWING=71, 
		CURRENT=72, FIRST=73, AFTER=74, LAST=75, ROW=76, WITH=77, ERROR=78, WITHIN=79, 
		CONFIDENCE=80, DISTANCE=81, RTREE=82, KNN=83, HASHMAP=84, TREEMAP=85, 
		VALUES=86, CREATE=87, TABLE=88, DIRECTORY=89, VIEW=90, REPLACE=91, INSERT=92, 
		DELETE=93, INTO=94, DESCRIBE=95, EXPLAIN=96, FORMAT=97, LOGICAL=98, CODEGEN=99, 
		COST=100, CAST=101, SHOW=102, TABLES=103, COLUMNS=104, COLUMN=105, USE=106, 
		PARTITIONS=107, FUNCTIONS=108, DROP=109, UNION=110, EXCEPT=111, SETMINUS=112, 
		INTERSECT=113, TO=114, TABLESAMPLE=115, STRATIFY=116, ALTER=117, RENAME=118, 
		ARRAY=119, MAP=120, STRUCT=121, COMMENT=122, SET=123, RESET=124, DATA=125, 
		START=126, TRANSACTION=127, COMMIT=128, ROLLBACK=129, MACRO=130, IGNORE=131, 
		BOTH=132, LEADING=133, TRAILING=134, IF=135, POSITION=136, EXTRACT=137, 
		EQ=138, NSEQ=139, NEQ=140, NEQJ=141, LT=142, LTE=143, GT=144, GTE=145, 
		PLUS=146, MINUS=147, ASTERISK=148, SLASH=149, PERCENT=150, DIV=151, TILDE=152, 
		AMPERSAND=153, PIPE=154, CONCAT_PIPE=155, HAT=156, PERCENTLIT=157, BUCKET=158, 
		OUT=159, OF=160, SORT=161, CLUSTER=162, DISTRIBUTE=163, OVERWRITE=164, 
		TRANSFORM=165, REDUCE=166, USING=167, SERDE=168, SERDEPROPERTIES=169, 
		RECORDREADER=170, RECORDWRITER=171, DELIMITED=172, FIELDS=173, TERMINATED=174, 
		COLLECTION=175, ITEMS=176, KEYS=177, ESCAPED=178, LINES=179, SEPARATED=180, 
		FUNCTION=181, EXTENDED=182, REFRESH=183, CLEAR=184, CACHE=185, UNCACHE=186, 
		LAZY=187, FORMATTED=188, GLOBAL=189, TEMPORARY=190, OPTIONS=191, UNSET=192, 
		TBLPROPERTIES=193, DBPROPERTIES=194, BUCKETS=195, SKEWED=196, STORED=197, 
		DIRECTORIES=198, LOCATION=199, EXCHANGE=200, ARCHIVE=201, UNARCHIVE=202, 
		FILEFORMAT=203, TOUCH=204, COMPACT=205, CONCATENATE=206, CHANGE=207, CASCADE=208, 
		RESTRICT=209, CLUSTERED=210, SORTED=211, PURGE=212, INPUTFORMAT=213, OUTPUTFORMAT=214, 
		DATABASE=215, DATABASES=216, DFS=217, TRUNCATE=218, ANALYZE=219, COMPUTE=220, 
		LIST=221, STATISTICS=222, PARTITIONED=223, EXTERNAL=224, DEFINED=225, 
		REVOKE=226, GRANT=227, LOCK=228, UNLOCK=229, MSCK=230, REPAIR=231, RECOVER=232, 
		EXPORT=233, IMPORT=234, LOAD=235, ROLE=236, ROLES=237, COMPACTIONS=238, 
		PRINCIPALS=239, TRANSACTIONS=240, INDEX=241, INDEXES=242, LOCKS=243, OPTION=244, 
		ANTI=245, LOCAL=246, INPATH=247, STRING=248, BIGINT_LITERAL=249, SMALLINT_LITERAL=250, 
		TINYINT_LITERAL=251, INTEGER_VALUE=252, DECIMAL_VALUE=253, DOUBLE_LITERAL=254, 
		BIGDECIMAL_LITERAL=255, PERCENTAGE=256, IDENTIFIER=257, BACKQUOTED_IDENTIFIER=258, 
		SIMPLE_COMMENT=259, BRACKETED_EMPTY_COMMENT=260, BRACKETED_COMMENT=261, 
		WS=262, UNRECOGNIZED=263;
	public static final int
		RULE_singleStatement = 0, RULE_singleExpression = 1, RULE_singleTableIdentifier = 2, 
		RULE_singleFunctionIdentifier = 3, RULE_singleDataType = 4, RULE_singleTableSchema = 5, 
		RULE_statement = 6, RULE_indexType = 7, RULE_unsupportedHiveNativeCommands = 8, 
		RULE_createTableHeader = 9, RULE_bucketSpec = 10, RULE_skewSpec = 11, 
		RULE_locationSpec = 12, RULE_query = 13, RULE_insertInto = 14, RULE_partitionSpecLocation = 15, 
		RULE_partitionSpec = 16, RULE_partitionVal = 17, RULE_describeFuncName = 18, 
		RULE_describeColName = 19, RULE_ctes = 20, RULE_namedQuery = 21, RULE_tableProvider = 22, 
		RULE_tablePropertyList = 23, RULE_tableProperty = 24, RULE_tablePropertyKey = 25, 
		RULE_tablePropertyValue = 26, RULE_constantList = 27, RULE_nestedConstantList = 28, 
		RULE_createFileFormat = 29, RULE_fileFormat = 30, RULE_storageHandler = 31, 
		RULE_resource = 32, RULE_queryNoWith = 33, RULE_queryOrganization = 34, 
		RULE_multiInsertQueryBody = 35, RULE_queryTerm = 36, RULE_queryPrimary = 37, 
		RULE_sortItem = 38, RULE_querySpecification = 39, RULE_aqp = 40, RULE_error = 41, 
		RULE_confidence = 42, RULE_hint = 43, RULE_hintStatement = 44, RULE_fromClause = 45, 
		RULE_aggregation = 46, RULE_groupingSet = 47, RULE_pivotClause = 48, RULE_pivotColumn = 49, 
		RULE_pivotValue = 50, RULE_lateralView = 51, RULE_setQuantifier = 52, 
		RULE_relation = 53, RULE_joinRelation = 54, RULE_joinType = 55, RULE_joinCriteria = 56, 
		RULE_sample = 57, RULE_sampleMethod = 58, RULE_identifierList = 59, RULE_identifierSeq = 60, 
		RULE_orderedIdentifierList = 61, RULE_orderedIdentifier = 62, RULE_identifierCommentList = 63, 
		RULE_identifierComment = 64, RULE_relationPrimary = 65, RULE_inlineTable = 66, 
		RULE_functionTable = 67, RULE_tableAlias = 68, RULE_rowFormat = 69, RULE_tableIdentifier = 70, 
		RULE_functionIdentifier = 71, RULE_namedExpression = 72, RULE_namedExpressionSeq = 73, 
		RULE_expression = 74, RULE_booleanExpression = 75, RULE_predicate = 76, 
		RULE_valueExpression = 77, RULE_primaryExpression = 78, RULE_constant = 79, 
		RULE_comparisonOperator = 80, RULE_arithmeticOperator = 81, RULE_predicateOperator = 82, 
		RULE_booleanValue = 83, RULE_interval = 84, RULE_intervalField = 85, RULE_intervalValue = 86, 
		RULE_colPosition = 87, RULE_dataType = 88, RULE_colTypeList = 89, RULE_colType = 90, 
		RULE_complexColTypeList = 91, RULE_complexColType = 92, RULE_whenClause = 93, 
		RULE_windows = 94, RULE_namedWindow = 95, RULE_windowSpec = 96, RULE_windowFrame = 97, 
		RULE_frameBound = 98, RULE_qualifiedName = 99, RULE_identifier = 100, 
		RULE_strictIdentifier = 101, RULE_quotedIdentifier = 102, RULE_number = 103, 
		RULE_nonReserved = 104;
	private static String[] makeRuleNames() {
		return new String[] {
			"singleStatement", "singleExpression", "singleTableIdentifier", "singleFunctionIdentifier", 
			"singleDataType", "singleTableSchema", "statement", "indexType", "unsupportedHiveNativeCommands", 
			"createTableHeader", "bucketSpec", "skewSpec", "locationSpec", "query", 
			"insertInto", "partitionSpecLocation", "partitionSpec", "partitionVal", 
			"describeFuncName", "describeColName", "ctes", "namedQuery", "tableProvider", 
			"tablePropertyList", "tableProperty", "tablePropertyKey", "tablePropertyValue", 
			"constantList", "nestedConstantList", "createFileFormat", "fileFormat", 
			"storageHandler", "resource", "queryNoWith", "queryOrganization", "multiInsertQueryBody", 
			"queryTerm", "queryPrimary", "sortItem", "querySpecification", "aqp", 
			"error", "confidence", "hint", "hintStatement", "fromClause", "aggregation", 
			"groupingSet", "pivotClause", "pivotColumn", "pivotValue", "lateralView", 
			"setQuantifier", "relation", "joinRelation", "joinType", "joinCriteria", 
			"sample", "sampleMethod", "identifierList", "identifierSeq", "orderedIdentifierList", 
			"orderedIdentifier", "identifierCommentList", "identifierComment", "relationPrimary", 
			"inlineTable", "functionTable", "tableAlias", "rowFormat", "tableIdentifier", 
			"functionIdentifier", "namedExpression", "namedExpressionSeq", "expression", 
			"booleanExpression", "predicate", "valueExpression", "primaryExpression", 
			"constant", "comparisonOperator", "arithmeticOperator", "predicateOperator", 
			"booleanValue", "interval", "intervalField", "intervalValue", "colPosition", 
			"dataType", "colTypeList", "colType", "complexColTypeList", "complexColType", 
			"whenClause", "windows", "namedWindow", "windowSpec", "windowFrame", 
			"frameBound", "qualifiedName", "identifier", "strictIdentifier", "quotedIdentifier", 
			"number", "nonReserved"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "','", "'.'", "'/*+'", "'*/'", "'->'", "'['", "']'", 
			"':'", "'SELECT'", "'FROM'", "'ADD'", "'AS'", "'ALL'", "'ANY'", "'DISTINCT'", 
			"'WHERE'", "'GROUP'", "'BY'", "'GROUPING'", "'SETS'", "'CUBE'", "'ROLLUP'", 
			"'ORDER'", "'HAVING'", "'LIMIT'", "'AT'", "'OR'", "'AND'", "'IN'", null, 
			"'NO'", "'EXISTS'", "'BETWEEN'", "'LIKE'", null, "'IS'", "'NULL'", "'TRUE'", 
			"'FALSE'", "'NULLS'", "'ASC'", "'DESC'", "'FOR'", "'INTERVAL'", "'CASE'", 
			"'WHEN'", "'THEN'", "'ELSE'", "'END'", "'JOIN'", "'CROSS'", "'OUTER'", 
			"'INNER'", "'LEFT'", "'SEMI'", "'RIGHT'", "'FULL'", "'NATURAL'", "'ON'", 
			"'PIVOT'", "'LATERAL'", "'WINDOW'", "'OVER'", "'PARTITION'", "'RANGE'", 
			"'ROWS'", "'UNBOUNDED'", "'PRECEDING'", "'FOLLOWING'", "'CURRENT'", "'FIRST'", 
			"'AFTER'", "'LAST'", "'ROW'", "'WITH'", "'ERROR'", "'WITHIN'", "'CONFIDENCE'", 
			"'DISTANCE'", "'RTREE'", "'KNN'", "'HASHMAP'", "'TREEMAP'", "'VALUES'", 
			"'CREATE'", "'TABLE'", "'DIRECTORY'", "'VIEW'", "'REPLACE'", "'INSERT'", 
			"'DELETE'", "'INTO'", "'DESCRIBE'", "'EXPLAIN'", "'FORMAT'", "'LOGICAL'", 
			"'CODEGEN'", "'COST'", "'CAST'", "'SHOW'", "'TABLES'", "'COLUMNS'", "'COLUMN'", 
			"'USE'", "'PARTITIONS'", "'FUNCTIONS'", "'DROP'", "'UNION'", "'EXCEPT'", 
			"'MINUS'", "'INTERSECT'", "'TO'", "'TABLESAMPLE'", "'STRATIFY'", "'ALTER'", 
			"'RENAME'", "'ARRAY'", "'MAP'", "'STRUCT'", "'COMMENT'", "'SET'", "'RESET'", 
			"'DATA'", "'START'", "'TRANSACTION'", "'COMMIT'", "'ROLLBACK'", "'MACRO'", 
			"'IGNORE'", "'BOTH'", "'LEADING'", "'TRAILING'", "'IF'", "'POSITION'", 
			"'EXTRACT'", null, "'<=>'", "'<>'", "'!='", "'<'", null, "'>'", null, 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'DIV'", "'~'", "'&'", "'|'", "'||'", 
			"'^'", "'PERCENT'", "'BUCKET'", "'OUT'", "'OF'", "'SORT'", "'CLUSTER'", 
			"'DISTRIBUTE'", "'OVERWRITE'", "'TRANSFORM'", "'REDUCE'", "'USING'", 
			"'SERDE'", "'SERDEPROPERTIES'", "'RECORDREADER'", "'RECORDWRITER'", "'DELIMITED'", 
			"'FIELDS'", "'TERMINATED'", "'COLLECTION'", "'ITEMS'", "'KEYS'", "'ESCAPED'", 
			"'LINES'", "'SEPARATED'", "'FUNCTION'", "'EXTENDED'", "'REFRESH'", "'CLEAR'", 
			"'CACHE'", "'UNCACHE'", "'LAZY'", "'FORMATTED'", "'GLOBAL'", null, "'OPTIONS'", 
			"'UNSET'", "'TBLPROPERTIES'", "'DBPROPERTIES'", "'BUCKETS'", "'SKEWED'", 
			"'STORED'", "'DIRECTORIES'", "'LOCATION'", "'EXCHANGE'", "'ARCHIVE'", 
			"'UNARCHIVE'", "'FILEFORMAT'", "'TOUCH'", "'COMPACT'", "'CONCATENATE'", 
			"'CHANGE'", "'CASCADE'", "'RESTRICT'", "'CLUSTERED'", "'SORTED'", "'PURGE'", 
			"'INPUTFORMAT'", "'OUTPUTFORMAT'", null, null, "'DFS'", "'TRUNCATE'", 
			"'ANALYZE'", "'COMPUTE'", "'LIST'", "'STATISTICS'", "'PARTITIONED'", 
			"'EXTERNAL'", "'DEFINED'", "'REVOKE'", "'GRANT'", "'LOCK'", "'UNLOCK'", 
			"'MSCK'", "'REPAIR'", "'RECOVER'", "'EXPORT'", "'IMPORT'", "'LOAD'", 
			"'ROLE'", "'ROLES'", "'COMPACTIONS'", "'PRINCIPALS'", "'TRANSACTIONS'", 
			"'INDEX'", "'INDEXES'", "'LOCKS'", "'OPTION'", "'ANTI'", "'LOCAL'", "'INPATH'", 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"'/**/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "SELECT", 
			"FROM", "ADD", "AS", "ALL", "ANY", "DISTINCT", "WHERE", "GROUP", "BY", 
			"GROUPING", "SETS", "CUBE", "ROLLUP", "ORDER", "HAVING", "LIMIT", "AT", 
			"OR", "AND", "IN", "NOT", "NO", "EXISTS", "BETWEEN", "LIKE", "RLIKE", 
			"IS", "NULL", "TRUE", "FALSE", "NULLS", "ASC", "DESC", "FOR", "INTERVAL", 
			"CASE", "WHEN", "THEN", "ELSE", "END", "JOIN", "CROSS", "OUTER", "INNER", 
			"LEFT", "SEMI", "RIGHT", "FULL", "NATURAL", "ON", "PIVOT", "LATERAL", 
			"WINDOW", "OVER", "PARTITION", "RANGE", "ROWS", "UNBOUNDED", "PRECEDING", 
			"FOLLOWING", "CURRENT", "FIRST", "AFTER", "LAST", "ROW", "WITH", "ERROR", 
			"WITHIN", "CONFIDENCE", "DISTANCE", "RTREE", "KNN", "HASHMAP", "TREEMAP", 
			"VALUES", "CREATE", "TABLE", "DIRECTORY", "VIEW", "REPLACE", "INSERT", 
			"DELETE", "INTO", "DESCRIBE", "EXPLAIN", "FORMAT", "LOGICAL", "CODEGEN", 
			"COST", "CAST", "SHOW", "TABLES", "COLUMNS", "COLUMN", "USE", "PARTITIONS", 
			"FUNCTIONS", "DROP", "UNION", "EXCEPT", "SETMINUS", "INTERSECT", "TO", 
			"TABLESAMPLE", "STRATIFY", "ALTER", "RENAME", "ARRAY", "MAP", "STRUCT", 
			"COMMENT", "SET", "RESET", "DATA", "START", "TRANSACTION", "COMMIT", 
			"ROLLBACK", "MACRO", "IGNORE", "BOTH", "LEADING", "TRAILING", "IF", "POSITION", 
			"EXTRACT", "EQ", "NSEQ", "NEQ", "NEQJ", "LT", "LTE", "GT", "GTE", "PLUS", 
			"MINUS", "ASTERISK", "SLASH", "PERCENT", "DIV", "TILDE", "AMPERSAND", 
			"PIPE", "CONCAT_PIPE", "HAT", "PERCENTLIT", "BUCKET", "OUT", "OF", "SORT", 
			"CLUSTER", "DISTRIBUTE", "OVERWRITE", "TRANSFORM", "REDUCE", "USING", 
			"SERDE", "SERDEPROPERTIES", "RECORDREADER", "RECORDWRITER", "DELIMITED", 
			"FIELDS", "TERMINATED", "COLLECTION", "ITEMS", "KEYS", "ESCAPED", "LINES", 
			"SEPARATED", "FUNCTION", "EXTENDED", "REFRESH", "CLEAR", "CACHE", "UNCACHE", 
			"LAZY", "FORMATTED", "GLOBAL", "TEMPORARY", "OPTIONS", "UNSET", "TBLPROPERTIES", 
			"DBPROPERTIES", "BUCKETS", "SKEWED", "STORED", "DIRECTORIES", "LOCATION", 
			"EXCHANGE", "ARCHIVE", "UNARCHIVE", "FILEFORMAT", "TOUCH", "COMPACT", 
			"CONCATENATE", "CHANGE", "CASCADE", "RESTRICT", "CLUSTERED", "SORTED", 
			"PURGE", "INPUTFORMAT", "OUTPUTFORMAT", "DATABASE", "DATABASES", "DFS", 
			"TRUNCATE", "ANALYZE", "COMPUTE", "LIST", "STATISTICS", "PARTITIONED", 
			"EXTERNAL", "DEFINED", "REVOKE", "GRANT", "LOCK", "UNLOCK", "MSCK", "REPAIR", 
			"RECOVER", "EXPORT", "IMPORT", "LOAD", "ROLE", "ROLES", "COMPACTIONS", 
			"PRINCIPALS", "TRANSACTIONS", "INDEX", "INDEXES", "LOCKS", "OPTION", 
			"ANTI", "LOCAL", "INPATH", "STRING", "BIGINT_LITERAL", "SMALLINT_LITERAL", 
			"TINYINT_LITERAL", "INTEGER_VALUE", "DECIMAL_VALUE", "DOUBLE_LITERAL", 
			"BIGDECIMAL_LITERAL", "PERCENTAGE", "IDENTIFIER", "BACKQUOTED_IDENTIFIER", 
			"SIMPLE_COMMENT", "BRACKETED_EMPTY_COMMENT", "BRACKETED_COMMENT", "WS", 
			"UNRECOGNIZED"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "NewSqlBase.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	  /**
	   * When false, INTERSECT is given the greater precedence over the other set
	   * operations (UNION, EXCEPT and MINUS) as per the SQL standard.
	   */
	  public boolean legacy_setops_precedence_enbled = false;

	  /**
	   * Verify whether current token is a valid decimal token (which contains dot).
	   * Returns true if the character that follows the token is not a digit or letter or underscore.
	   *
	   * For example:
	   * For char stream "2.3", "2." is not a valid decimal token, because it is followed by digit '3'.
	   * For char stream "2.3_", "2.3" is not a valid decimal token, because it is followed by '_'.
	   * For char stream "2.3W", "2.3" is not a valid decimal token, because it is followed by 'W'.
	   * For char stream "12.0D 34.E2+0.12 "  12.0D is a valid decimal token because it is followed
	   * by a space. 34.E2 is a valid decimal token because it is followed by symbol '+'
	   * which is not a digit or letter or underscore.
	   */
	  public boolean isValidDecimal() {
	    int nextChar = _input.LA(1);
	    if (nextChar >= 'A' && nextChar <= 'Z' || nextChar >= '0' && nextChar <= '9' ||
	      nextChar == '_') {
	      return false;
	    } else {
	      return true;
	    }
	  }

	public NewSqlBaseParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class SingleStatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode EOF() { return getToken(NewSqlBaseParser.EOF, 0); }
		public SingleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleStatementContext singleStatement() throws RecognitionException {
		SingleStatementContext _localctx = new SingleStatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_singleStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			statement();
			setState(211);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleExpressionContext extends ParserRuleContext {
		public NamedExpressionContext namedExpression() {
			return getRuleContext(NamedExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(NewSqlBaseParser.EOF, 0); }
		public SingleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleExpressionContext singleExpression() throws RecognitionException {
		SingleExpressionContext _localctx = new SingleExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_singleExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			namedExpression();
			setState(214);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleTableIdentifierContext extends ParserRuleContext {
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode EOF() { return getToken(NewSqlBaseParser.EOF, 0); }
		public SingleTableIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleTableIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleTableIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleTableIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleTableIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleTableIdentifierContext singleTableIdentifier() throws RecognitionException {
		SingleTableIdentifierContext _localctx = new SingleTableIdentifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_singleTableIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			tableIdentifier();
			setState(217);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleFunctionIdentifierContext extends ParserRuleContext {
		public FunctionIdentifierContext functionIdentifier() {
			return getRuleContext(FunctionIdentifierContext.class,0);
		}
		public TerminalNode EOF() { return getToken(NewSqlBaseParser.EOF, 0); }
		public SingleFunctionIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleFunctionIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleFunctionIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleFunctionIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleFunctionIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleFunctionIdentifierContext singleFunctionIdentifier() throws RecognitionException {
		SingleFunctionIdentifierContext _localctx = new SingleFunctionIdentifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_singleFunctionIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			functionIdentifier();
			setState(220);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleDataTypeContext extends ParserRuleContext {
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public TerminalNode EOF() { return getToken(NewSqlBaseParser.EOF, 0); }
		public SingleDataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleDataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleDataTypeContext singleDataType() throws RecognitionException {
		SingleDataTypeContext _localctx = new SingleDataTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_singleDataType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			dataType();
			setState(223);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleTableSchemaContext extends ParserRuleContext {
		public ColTypeListContext colTypeList() {
			return getRuleContext(ColTypeListContext.class,0);
		}
		public TerminalNode EOF() { return getToken(NewSqlBaseParser.EOF, 0); }
		public SingleTableSchemaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleTableSchema; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleTableSchema(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleTableSchema(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleTableSchema(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleTableSchemaContext singleTableSchema() throws RecognitionException {
		SingleTableSchemaContext _localctx = new SingleTableSchemaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_singleTableSchema);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			colTypeList();
			setState(226);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExplainContext extends StatementContext {
		public TerminalNode EXPLAIN() { return getToken(NewSqlBaseParser.EXPLAIN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode LOGICAL() { return getToken(NewSqlBaseParser.LOGICAL, 0); }
		public TerminalNode FORMATTED() { return getToken(NewSqlBaseParser.FORMATTED, 0); }
		public TerminalNode EXTENDED() { return getToken(NewSqlBaseParser.EXTENDED, 0); }
		public TerminalNode CODEGEN() { return getToken(NewSqlBaseParser.CODEGEN, 0); }
		public TerminalNode COST() { return getToken(NewSqlBaseParser.COST, 0); }
		public ExplainContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterExplain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitExplain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitExplain(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DropDatabaseContext extends StatementContext {
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public TerminalNode DATABASE() { return getToken(NewSqlBaseParser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public TerminalNode RESTRICT() { return getToken(NewSqlBaseParser.RESTRICT, 0); }
		public TerminalNode CASCADE() { return getToken(NewSqlBaseParser.CASCADE, 0); }
		public DropDatabaseContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDropDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDropDatabase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDropDatabase(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ResetConfigurationContext extends StatementContext {
		public TerminalNode RESET() { return getToken(NewSqlBaseParser.RESET, 0); }
		public ResetConfigurationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterResetConfiguration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitResetConfiguration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitResetConfiguration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DescribeDatabaseContext extends StatementContext {
		public TerminalNode DATABASE() { return getToken(NewSqlBaseParser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode DESC() { return getToken(NewSqlBaseParser.DESC, 0); }
		public TerminalNode DESCRIBE() { return getToken(NewSqlBaseParser.DESCRIBE, 0); }
		public TerminalNode EXTENDED() { return getToken(NewSqlBaseParser.EXTENDED, 0); }
		public DescribeDatabaseContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDescribeDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDescribeDatabase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDescribeDatabase(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AlterViewQueryContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public AlterViewQueryContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAlterViewQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAlterViewQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAlterViewQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UseContext extends StatementContext {
		public IdentifierContext db;
		public TerminalNode USE() { return getToken(NewSqlBaseParser.USE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UseContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterUse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitUse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitUse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateTempViewUsingContext extends StatementContext {
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TableProviderContext tableProvider() {
			return getRuleContext(TableProviderContext.class,0);
		}
		public TerminalNode OR() { return getToken(NewSqlBaseParser.OR, 0); }
		public TerminalNode REPLACE() { return getToken(NewSqlBaseParser.REPLACE, 0); }
		public TerminalNode GLOBAL() { return getToken(NewSqlBaseParser.GLOBAL, 0); }
		public ColTypeListContext colTypeList() {
			return getRuleContext(ColTypeListContext.class,0);
		}
		public TerminalNode OPTIONS() { return getToken(NewSqlBaseParser.OPTIONS, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public CreateTempViewUsingContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateTempViewUsing(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateTempViewUsing(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateTempViewUsing(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RenameTableContext extends StatementContext {
		public TableIdentifierContext from;
		public TableIdentifierContext to;
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode RENAME() { return getToken(NewSqlBaseParser.RENAME, 0); }
		public TerminalNode TO() { return getToken(NewSqlBaseParser.TO, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public List<TableIdentifierContext> tableIdentifier() {
			return getRuleContexts(TableIdentifierContext.class);
		}
		public TableIdentifierContext tableIdentifier(int i) {
			return getRuleContext(TableIdentifierContext.class,i);
		}
		public RenameTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRenameTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRenameTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRenameTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FailNativeCommandContext extends StatementContext {
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public TerminalNode ROLE() { return getToken(NewSqlBaseParser.ROLE, 0); }
		public UnsupportedHiveNativeCommandsContext unsupportedHiveNativeCommands() {
			return getRuleContext(UnsupportedHiveNativeCommandsContext.class,0);
		}
		public FailNativeCommandContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFailNativeCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFailNativeCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFailNativeCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ClearCacheContext extends StatementContext {
		public TerminalNode CLEAR() { return getToken(NewSqlBaseParser.CLEAR, 0); }
		public TerminalNode CACHE() { return getToken(NewSqlBaseParser.CACHE, 0); }
		public ClearCacheContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterClearCache(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitClearCache(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitClearCache(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowTablesContext extends StatementContext {
		public IdentifierContext db;
		public Token pattern;
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode TABLES() { return getToken(NewSqlBaseParser.TABLES, 0); }
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public ShowTablesContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowTables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowTables(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowTables(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecoverPartitionsContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode RECOVER() { return getToken(NewSqlBaseParser.RECOVER, 0); }
		public TerminalNode PARTITIONS() { return getToken(NewSqlBaseParser.PARTITIONS, 0); }
		public RecoverPartitionsContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRecoverPartitions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRecoverPartitions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRecoverPartitions(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RenameTablePartitionContext extends StatementContext {
		public PartitionSpecContext from;
		public PartitionSpecContext to;
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode RENAME() { return getToken(NewSqlBaseParser.RENAME, 0); }
		public TerminalNode TO() { return getToken(NewSqlBaseParser.TO, 0); }
		public List<PartitionSpecContext> partitionSpec() {
			return getRuleContexts(PartitionSpecContext.class);
		}
		public PartitionSpecContext partitionSpec(int i) {
			return getRuleContext(PartitionSpecContext.class,i);
		}
		public RenameTablePartitionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRenameTablePartition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRenameTablePartition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRenameTablePartition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RepairTableContext extends StatementContext {
		public TerminalNode MSCK() { return getToken(NewSqlBaseParser.MSCK, 0); }
		public TerminalNode REPAIR() { return getToken(NewSqlBaseParser.REPAIR, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public RepairTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRepairTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRepairTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRepairTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RefreshResourceContext extends StatementContext {
		public TerminalNode REFRESH() { return getToken(NewSqlBaseParser.REFRESH, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public RefreshResourceContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRefreshResource(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRefreshResource(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRefreshResource(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowCreateTableContext extends StatementContext {
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public ShowCreateTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowCreateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowCreateTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowColumnsContext extends StatementContext {
		public IdentifierContext db;
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode COLUMNS() { return getToken(NewSqlBaseParser.COLUMNS, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public List<TerminalNode> FROM() { return getTokens(NewSqlBaseParser.FROM); }
		public TerminalNode FROM(int i) {
			return getToken(NewSqlBaseParser.FROM, i);
		}
		public List<TerminalNode> IN() { return getTokens(NewSqlBaseParser.IN); }
		public TerminalNode IN(int i) {
			return getToken(NewSqlBaseParser.IN, i);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ShowColumnsContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowColumns(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowColumns(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowColumns(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddTablePartitionContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode ADD() { return getToken(NewSqlBaseParser.ADD, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public List<PartitionSpecLocationContext> partitionSpecLocation() {
			return getRuleContexts(PartitionSpecLocationContext.class);
		}
		public PartitionSpecLocationContext partitionSpecLocation(int i) {
			return getRuleContext(PartitionSpecLocationContext.class,i);
		}
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public List<PartitionSpecContext> partitionSpec() {
			return getRuleContexts(PartitionSpecContext.class);
		}
		public PartitionSpecContext partitionSpec(int i) {
			return getRuleContext(PartitionSpecContext.class,i);
		}
		public AddTablePartitionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAddTablePartition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAddTablePartition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAddTablePartition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RefreshTableContext extends StatementContext {
		public TerminalNode REFRESH() { return getToken(NewSqlBaseParser.REFRESH, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public RefreshTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRefreshTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRefreshTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRefreshTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ManageResourceContext extends StatementContext {
		public Token op;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ADD() { return getToken(NewSqlBaseParser.ADD, 0); }
		public TerminalNode LIST() { return getToken(NewSqlBaseParser.LIST, 0); }
		public ManageResourceContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterManageResource(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitManageResource(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitManageResource(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateDatabaseContext extends StatementContext {
		public Token comment;
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode DATABASE() { return getToken(NewSqlBaseParser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public TerminalNode COMMENT() { return getToken(NewSqlBaseParser.COMMENT, 0); }
		public LocationSpecContext locationSpec() {
			return getRuleContext(LocationSpecContext.class,0);
		}
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public TerminalNode DBPROPERTIES() { return getToken(NewSqlBaseParser.DBPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public CreateDatabaseContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateDatabase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateDatabase(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AnalyzeContext extends StatementContext {
		public TerminalNode ANALYZE() { return getToken(NewSqlBaseParser.ANALYZE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode COMPUTE() { return getToken(NewSqlBaseParser.COMPUTE, 0); }
		public TerminalNode STATISTICS() { return getToken(NewSqlBaseParser.STATISTICS, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FOR() { return getToken(NewSqlBaseParser.FOR, 0); }
		public TerminalNode COLUMNS() { return getToken(NewSqlBaseParser.COLUMNS, 0); }
		public IdentifierSeqContext identifierSeq() {
			return getRuleContext(IdentifierSeqContext.class,0);
		}
		public AnalyzeContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAnalyze(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAnalyze(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAnalyze(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateHiveTableContext extends StatementContext {
		public ColTypeListContext columns;
		public Token comment;
		public ColTypeListContext partitionColumns;
		public TablePropertyListContext tableProps;
		public CreateTableHeaderContext createTableHeader() {
			return getRuleContext(CreateTableHeaderContext.class,0);
		}
		public List<BucketSpecContext> bucketSpec() {
			return getRuleContexts(BucketSpecContext.class);
		}
		public BucketSpecContext bucketSpec(int i) {
			return getRuleContext(BucketSpecContext.class,i);
		}
		public List<SkewSpecContext> skewSpec() {
			return getRuleContexts(SkewSpecContext.class);
		}
		public SkewSpecContext skewSpec(int i) {
			return getRuleContext(SkewSpecContext.class,i);
		}
		public List<RowFormatContext> rowFormat() {
			return getRuleContexts(RowFormatContext.class);
		}
		public RowFormatContext rowFormat(int i) {
			return getRuleContext(RowFormatContext.class,i);
		}
		public List<CreateFileFormatContext> createFileFormat() {
			return getRuleContexts(CreateFileFormatContext.class);
		}
		public CreateFileFormatContext createFileFormat(int i) {
			return getRuleContext(CreateFileFormatContext.class,i);
		}
		public List<LocationSpecContext> locationSpec() {
			return getRuleContexts(LocationSpecContext.class);
		}
		public LocationSpecContext locationSpec(int i) {
			return getRuleContext(LocationSpecContext.class,i);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public List<ColTypeListContext> colTypeList() {
			return getRuleContexts(ColTypeListContext.class);
		}
		public ColTypeListContext colTypeList(int i) {
			return getRuleContext(ColTypeListContext.class,i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(NewSqlBaseParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(NewSqlBaseParser.COMMENT, i);
		}
		public List<TerminalNode> PARTITIONED() { return getTokens(NewSqlBaseParser.PARTITIONED); }
		public TerminalNode PARTITIONED(int i) {
			return getToken(NewSqlBaseParser.PARTITIONED, i);
		}
		public List<TerminalNode> BY() { return getTokens(NewSqlBaseParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(NewSqlBaseParser.BY, i);
		}
		public List<TerminalNode> TBLPROPERTIES() { return getTokens(NewSqlBaseParser.TBLPROPERTIES); }
		public TerminalNode TBLPROPERTIES(int i) {
			return getToken(NewSqlBaseParser.TBLPROPERTIES, i);
		}
		public List<TerminalNode> STRING() { return getTokens(NewSqlBaseParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(NewSqlBaseParser.STRING, i);
		}
		public List<TablePropertyListContext> tablePropertyList() {
			return getRuleContexts(TablePropertyListContext.class);
		}
		public TablePropertyListContext tablePropertyList(int i) {
			return getRuleContext(TablePropertyListContext.class,i);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public CreateHiveTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateHiveTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateHiveTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateHiveTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateFunctionContext extends StatementContext {
		public Token className;
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode FUNCTION() { return getToken(NewSqlBaseParser.FUNCTION, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode OR() { return getToken(NewSqlBaseParser.OR, 0); }
		public TerminalNode REPLACE() { return getToken(NewSqlBaseParser.REPLACE, 0); }
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public TerminalNode USING() { return getToken(NewSqlBaseParser.USING, 0); }
		public List<ResourceContext> resource() {
			return getRuleContexts(ResourceContext.class);
		}
		public ResourceContext resource(int i) {
			return getRuleContext(ResourceContext.class,i);
		}
		public CreateFunctionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowTableContext extends StatementContext {
		public IdentifierContext db;
		public Token pattern;
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode EXTENDED() { return getToken(NewSqlBaseParser.EXTENDED, 0); }
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ShowTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetDatabasePropertiesContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode DATABASE() { return getToken(NewSqlBaseParser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public TerminalNode DBPROPERTIES() { return getToken(NewSqlBaseParser.DBPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public SetDatabasePropertiesContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetDatabaseProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetDatabaseProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetDatabaseProperties(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateTableContext extends StatementContext {
		public TablePropertyListContext options;
		public IdentifierListContext partitionColumnNames;
		public Token comment;
		public TablePropertyListContext tableProps;
		public CreateTableHeaderContext createTableHeader() {
			return getRuleContext(CreateTableHeaderContext.class,0);
		}
		public TableProviderContext tableProvider() {
			return getRuleContext(TableProviderContext.class,0);
		}
		public ColTypeListContext colTypeList() {
			return getRuleContext(ColTypeListContext.class,0);
		}
		public List<BucketSpecContext> bucketSpec() {
			return getRuleContexts(BucketSpecContext.class);
		}
		public BucketSpecContext bucketSpec(int i) {
			return getRuleContext(BucketSpecContext.class,i);
		}
		public List<LocationSpecContext> locationSpec() {
			return getRuleContexts(LocationSpecContext.class);
		}
		public LocationSpecContext locationSpec(int i) {
			return getRuleContext(LocationSpecContext.class,i);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public List<TerminalNode> OPTIONS() { return getTokens(NewSqlBaseParser.OPTIONS); }
		public TerminalNode OPTIONS(int i) {
			return getToken(NewSqlBaseParser.OPTIONS, i);
		}
		public List<TerminalNode> PARTITIONED() { return getTokens(NewSqlBaseParser.PARTITIONED); }
		public TerminalNode PARTITIONED(int i) {
			return getToken(NewSqlBaseParser.PARTITIONED, i);
		}
		public List<TerminalNode> BY() { return getTokens(NewSqlBaseParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(NewSqlBaseParser.BY, i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(NewSqlBaseParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(NewSqlBaseParser.COMMENT, i);
		}
		public List<TerminalNode> TBLPROPERTIES() { return getTokens(NewSqlBaseParser.TBLPROPERTIES); }
		public TerminalNode TBLPROPERTIES(int i) {
			return getToken(NewSqlBaseParser.TBLPROPERTIES, i);
		}
		public List<TablePropertyListContext> tablePropertyList() {
			return getRuleContexts(TablePropertyListContext.class);
		}
		public TablePropertyListContext tablePropertyList(int i) {
			return getRuleContext(TablePropertyListContext.class,i);
		}
		public List<IdentifierListContext> identifierList() {
			return getRuleContexts(IdentifierListContext.class);
		}
		public IdentifierListContext identifierList(int i) {
			return getRuleContext(IdentifierListContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(NewSqlBaseParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(NewSqlBaseParser.STRING, i);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public CreateTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DescribeTableContext extends StatementContext {
		public Token option;
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode DESC() { return getToken(NewSqlBaseParser.DESC, 0); }
		public TerminalNode DESCRIBE() { return getToken(NewSqlBaseParser.DESCRIBE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public DescribeColNameContext describeColName() {
			return getRuleContext(DescribeColNameContext.class,0);
		}
		public TerminalNode EXTENDED() { return getToken(NewSqlBaseParser.EXTENDED, 0); }
		public TerminalNode FORMATTED() { return getToken(NewSqlBaseParser.FORMATTED, 0); }
		public DescribeTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDescribeTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDescribeTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDescribeTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateTableLikeContext extends StatementContext {
		public TableIdentifierContext target;
		public TableIdentifierContext source;
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public List<TableIdentifierContext> tableIdentifier() {
			return getRuleContexts(TableIdentifierContext.class);
		}
		public TableIdentifierContext tableIdentifier(int i) {
			return getRuleContext(TableIdentifierContext.class,i);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public LocationSpecContext locationSpec() {
			return getRuleContext(LocationSpecContext.class,0);
		}
		public CreateTableLikeContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateTableLike(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateTableLike(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateTableLike(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UncacheTableContext extends StatementContext {
		public TerminalNode UNCACHE() { return getToken(NewSqlBaseParser.UNCACHE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public UncacheTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterUncacheTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitUncacheTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitUncacheTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DropFunctionContext extends StatementContext {
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public TerminalNode FUNCTION() { return getToken(NewSqlBaseParser.FUNCTION, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public DropFunctionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDropFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDropFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDropFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowIndexOnRelationContext extends StatementContext {
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode INDEX() { return getToken(NewSqlBaseParser.INDEX, 0); }
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public RelationContext relation() {
			return getRuleContext(RelationContext.class,0);
		}
		public ShowIndexOnRelationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowIndexOnRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowIndexOnRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowIndexOnRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadDataContext extends StatementContext {
		public Token path;
		public TerminalNode LOAD() { return getToken(NewSqlBaseParser.LOAD, 0); }
		public TerminalNode DATA() { return getToken(NewSqlBaseParser.DATA, 0); }
		public TerminalNode INPATH() { return getToken(NewSqlBaseParser.INPATH, 0); }
		public TerminalNode INTO() { return getToken(NewSqlBaseParser.INTO, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode LOCAL() { return getToken(NewSqlBaseParser.LOCAL, 0); }
		public TerminalNode OVERWRITE() { return getToken(NewSqlBaseParser.OVERWRITE, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public LoadDataContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLoadData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLoadData(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLoadData(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowPartitionsContext extends StatementContext {
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode PARTITIONS() { return getToken(NewSqlBaseParser.PARTITIONS, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public ShowPartitionsContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowPartitions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowPartitions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowPartitions(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DescribeFunctionContext extends StatementContext {
		public TerminalNode FUNCTION() { return getToken(NewSqlBaseParser.FUNCTION, 0); }
		public DescribeFuncNameContext describeFuncName() {
			return getRuleContext(DescribeFuncNameContext.class,0);
		}
		public TerminalNode DESC() { return getToken(NewSqlBaseParser.DESC, 0); }
		public TerminalNode DESCRIBE() { return getToken(NewSqlBaseParser.DESCRIBE, 0); }
		public TerminalNode EXTENDED() { return getToken(NewSqlBaseParser.EXTENDED, 0); }
		public DescribeFunctionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDescribeFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDescribeFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDescribeFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateIndexCommandContext extends StatementContext {
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode INDEX() { return getToken(NewSqlBaseParser.INDEX, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public RelationContext relation() {
			return getRuleContext(RelationContext.class,0);
		}
		public TerminalNode USE() { return getToken(NewSqlBaseParser.USE, 0); }
		public IndexTypeContext indexType() {
			return getRuleContext(IndexTypeContext.class,0);
		}
		public CreateIndexCommandContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateIndexCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateIndexCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateIndexCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DropIndexOnRelationContext extends StatementContext {
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public TerminalNode INDEX() { return getToken(NewSqlBaseParser.INDEX, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public RelationContext relation() {
			return getRuleContext(RelationContext.class,0);
		}
		public DropIndexOnRelationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDropIndexOnRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDropIndexOnRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDropIndexOnRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChangeColumnContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode CHANGE() { return getToken(NewSqlBaseParser.CHANGE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ColTypeContext colType() {
			return getRuleContext(ColTypeContext.class,0);
		}
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TerminalNode COLUMN() { return getToken(NewSqlBaseParser.COLUMN, 0); }
		public ColPositionContext colPosition() {
			return getRuleContext(ColPositionContext.class,0);
		}
		public ChangeColumnContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterChangeColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitChangeColumn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitChangeColumn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StatementDefaultContext extends StatementContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public StatementDefaultContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterStatementDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitStatementDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitStatementDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TruncateTableContext extends StatementContext {
		public TerminalNode TRUNCATE() { return getToken(NewSqlBaseParser.TRUNCATE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TruncateTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTruncateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTruncateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTruncateTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetTableSerDeContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public TerminalNode SERDE() { return getToken(NewSqlBaseParser.SERDE, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public TerminalNode SERDEPROPERTIES() { return getToken(NewSqlBaseParser.SERDEPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public SetTableSerDeContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetTableSerDe(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetTableSerDe(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetTableSerDe(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreateViewContext extends StatementContext {
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode OR() { return getToken(NewSqlBaseParser.OR, 0); }
		public TerminalNode REPLACE() { return getToken(NewSqlBaseParser.REPLACE, 0); }
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public IdentifierCommentListContext identifierCommentList() {
			return getRuleContext(IdentifierCommentListContext.class,0);
		}
		public TerminalNode COMMENT() { return getToken(NewSqlBaseParser.COMMENT, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode PARTITIONED() { return getToken(NewSqlBaseParser.PARTITIONED, 0); }
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode TBLPROPERTIES() { return getToken(NewSqlBaseParser.TBLPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public TerminalNode GLOBAL() { return getToken(NewSqlBaseParser.GLOBAL, 0); }
		public CreateViewContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateView(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateView(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateView(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DropTablePartitionsContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public List<PartitionSpecContext> partitionSpec() {
			return getRuleContexts(PartitionSpecContext.class);
		}
		public PartitionSpecContext partitionSpec(int i) {
			return getRuleContext(PartitionSpecContext.class,i);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public TerminalNode PURGE() { return getToken(NewSqlBaseParser.PURGE, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public DropTablePartitionsContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDropTablePartitions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDropTablePartitions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDropTablePartitions(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetConfigurationContext extends StatementContext {
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public SetConfigurationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetConfiguration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetConfiguration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetConfiguration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DropTableContext extends StatementContext {
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public TerminalNode PURGE() { return getToken(NewSqlBaseParser.PURGE, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public DropTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDropTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDropTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDropTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowDatabasesContext extends StatementContext {
		public Token pattern;
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode DATABASES() { return getToken(NewSqlBaseParser.DATABASES, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public ShowDatabasesContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowDatabases(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowDatabases(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowDatabases(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowTblPropertiesContext extends StatementContext {
		public TableIdentifierContext table;
		public TablePropertyKeyContext key;
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode TBLPROPERTIES() { return getToken(NewSqlBaseParser.TBLPROPERTIES, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TablePropertyKeyContext tablePropertyKey() {
			return getRuleContext(TablePropertyKeyContext.class,0);
		}
		public ShowTblPropertiesContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowTblProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowTblProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowTblProperties(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnsetTablePropertiesContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode UNSET() { return getToken(NewSqlBaseParser.UNSET, 0); }
		public TerminalNode TBLPROPERTIES() { return getToken(NewSqlBaseParser.TBLPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public UnsetTablePropertiesContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterUnsetTableProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitUnsetTableProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitUnsetTableProperties(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetTableLocationContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public LocationSpecContext locationSpec() {
			return getRuleContext(LocationSpecContext.class,0);
		}
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public SetTableLocationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetTableLocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetTableLocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetTableLocation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShowFunctionsContext extends StatementContext {
		public Token pattern;
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode FUNCTIONS() { return getToken(NewSqlBaseParser.FUNCTIONS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public ShowFunctionsContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterShowFunctions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitShowFunctions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitShowFunctions(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CacheTableContext extends StatementContext {
		public TerminalNode CACHE() { return getToken(NewSqlBaseParser.CACHE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode LAZY() { return getToken(NewSqlBaseParser.LAZY, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public CacheTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCacheTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCacheTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCacheTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddTableColumnsContext extends StatementContext {
		public ColTypeListContext columns;
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode ADD() { return getToken(NewSqlBaseParser.ADD, 0); }
		public TerminalNode COLUMNS() { return getToken(NewSqlBaseParser.COLUMNS, 0); }
		public ColTypeListContext colTypeList() {
			return getRuleContext(ColTypeListContext.class,0);
		}
		public AddTableColumnsContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAddTableColumns(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAddTableColumns(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAddTableColumns(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetTablePropertiesContext extends StatementContext {
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public TerminalNode TBLPROPERTIES() { return getToken(NewSqlBaseParser.TBLPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public SetTablePropertiesContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetTableProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetTableProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetTableProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		int _la;
		try {
			int _alt;
			setState(852);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
			case 1:
				_localctx = new StatementDefaultContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				query();
				}
				break;
			case 2:
				_localctx = new UseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				match(USE);
				setState(230);
				((UseContext)_localctx).db = identifier();
				}
				break;
			case 3:
				_localctx = new CreateDatabaseContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(231);
				match(CREATE);
				setState(232);
				match(DATABASE);
				setState(236);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(233);
					match(IF);
					setState(234);
					match(NOT);
					setState(235);
					match(EXISTS);
					}
					break;
				}
				setState(238);
				identifier();
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(239);
					match(COMMENT);
					setState(240);
					((CreateDatabaseContext)_localctx).comment = match(STRING);
					}
				}

				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LOCATION) {
					{
					setState(243);
					locationSpec();
					}
				}

				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(246);
					match(WITH);
					setState(247);
					match(DBPROPERTIES);
					setState(248);
					tablePropertyList();
					}
				}

				}
				break;
			case 4:
				_localctx = new SetDatabasePropertiesContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(251);
				match(ALTER);
				setState(252);
				match(DATABASE);
				setState(253);
				identifier();
				setState(254);
				match(SET);
				setState(255);
				match(DBPROPERTIES);
				setState(256);
				tablePropertyList();
				}
				break;
			case 5:
				_localctx = new DropDatabaseContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(258);
				match(DROP);
				setState(259);
				match(DATABASE);
				setState(262);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(260);
					match(IF);
					setState(261);
					match(EXISTS);
					}
					break;
				}
				setState(264);
				identifier();
				setState(266);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CASCADE || _la==RESTRICT) {
					{
					setState(265);
					_la = _input.LA(1);
					if ( !(_la==CASCADE || _la==RESTRICT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
				break;
			case 6:
				_localctx = new CreateTableContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(268);
				createTableHeader();
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(269);
					match(T__0);
					setState(270);
					colTypeList();
					setState(271);
					match(T__1);
					}
				}

				setState(275);
				tableProvider();
				setState(289);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMENT || ((((_la - 191)) & ~0x3f) == 0 && ((1L << (_la - 191)) & ((1L << (OPTIONS - 191)) | (1L << (TBLPROPERTIES - 191)) | (1L << (LOCATION - 191)) | (1L << (CLUSTERED - 191)) | (1L << (PARTITIONED - 191)))) != 0)) {
					{
					setState(287);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case OPTIONS:
						{
						{
						setState(276);
						match(OPTIONS);
						setState(277);
						((CreateTableContext)_localctx).options = tablePropertyList();
						}
						}
						break;
					case PARTITIONED:
						{
						{
						setState(278);
						match(PARTITIONED);
						setState(279);
						match(BY);
						setState(280);
						((CreateTableContext)_localctx).partitionColumnNames = identifierList();
						}
						}
						break;
					case CLUSTERED:
						{
						setState(281);
						bucketSpec();
						}
						break;
					case LOCATION:
						{
						setState(282);
						locationSpec();
						}
						break;
					case COMMENT:
						{
						{
						setState(283);
						match(COMMENT);
						setState(284);
						((CreateTableContext)_localctx).comment = match(STRING);
						}
						}
						break;
					case TBLPROPERTIES:
						{
						{
						setState(285);
						match(TBLPROPERTIES);
						setState(286);
						((CreateTableContext)_localctx).tableProps = tablePropertyList();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(291);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << AS))) != 0) || ((((_la - 77)) & ~0x3f) == 0 && ((1L << (_la - 77)) & ((1L << (WITH - 77)) | (1L << (VALUES - 77)) | (1L << (TABLE - 77)) | (1L << (INSERT - 77)) | (1L << (MAP - 77)))) != 0) || _la==REDUCE) {
					{
					setState(293);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(292);
						match(AS);
						}
					}

					setState(295);
					query();
					}
				}

				}
				break;
			case 7:
				_localctx = new CreateHiveTableContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(298);
				createTableHeader();
				setState(303);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(299);
					match(T__0);
					setState(300);
					((CreateHiveTableContext)_localctx).columns = colTypeList();
					setState(301);
					match(T__1);
					}
					break;
				}
				setState(322);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ROW || _la==COMMENT || ((((_la - 193)) & ~0x3f) == 0 && ((1L << (_la - 193)) & ((1L << (TBLPROPERTIES - 193)) | (1L << (SKEWED - 193)) | (1L << (STORED - 193)) | (1L << (LOCATION - 193)) | (1L << (CLUSTERED - 193)) | (1L << (PARTITIONED - 193)))) != 0)) {
					{
					setState(320);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case COMMENT:
						{
						{
						setState(305);
						match(COMMENT);
						setState(306);
						((CreateHiveTableContext)_localctx).comment = match(STRING);
						}
						}
						break;
					case PARTITIONED:
						{
						{
						setState(307);
						match(PARTITIONED);
						setState(308);
						match(BY);
						setState(309);
						match(T__0);
						setState(310);
						((CreateHiveTableContext)_localctx).partitionColumns = colTypeList();
						setState(311);
						match(T__1);
						}
						}
						break;
					case CLUSTERED:
						{
						setState(313);
						bucketSpec();
						}
						break;
					case SKEWED:
						{
						setState(314);
						skewSpec();
						}
						break;
					case ROW:
						{
						setState(315);
						rowFormat();
						}
						break;
					case STORED:
						{
						setState(316);
						createFileFormat();
						}
						break;
					case LOCATION:
						{
						setState(317);
						locationSpec();
						}
						break;
					case TBLPROPERTIES:
						{
						{
						setState(318);
						match(TBLPROPERTIES);
						setState(319);
						((CreateHiveTableContext)_localctx).tableProps = tablePropertyList();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(324);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << AS))) != 0) || ((((_la - 77)) & ~0x3f) == 0 && ((1L << (_la - 77)) & ((1L << (WITH - 77)) | (1L << (VALUES - 77)) | (1L << (TABLE - 77)) | (1L << (INSERT - 77)) | (1L << (MAP - 77)))) != 0) || _la==REDUCE) {
					{
					setState(326);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(325);
						match(AS);
						}
					}

					setState(328);
					query();
					}
				}

				}
				break;
			case 8:
				_localctx = new CreateTableLikeContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(331);
				match(CREATE);
				setState(332);
				match(TABLE);
				setState(336);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(333);
					match(IF);
					setState(334);
					match(NOT);
					setState(335);
					match(EXISTS);
					}
					break;
				}
				setState(338);
				((CreateTableLikeContext)_localctx).target = tableIdentifier();
				setState(339);
				match(LIKE);
				setState(340);
				((CreateTableLikeContext)_localctx).source = tableIdentifier();
				setState(342);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LOCATION) {
					{
					setState(341);
					locationSpec();
					}
				}

				}
				break;
			case 9:
				_localctx = new AnalyzeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(344);
				match(ANALYZE);
				setState(345);
				match(TABLE);
				setState(346);
				tableIdentifier();
				setState(348);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(347);
					partitionSpec();
					}
				}

				setState(350);
				match(COMPUTE);
				setState(351);
				match(STATISTICS);
				setState(356);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(352);
					identifier();
					}
					break;
				case 2:
					{
					setState(353);
					match(FOR);
					setState(354);
					match(COLUMNS);
					setState(355);
					identifierSeq();
					}
					break;
				}
				}
				break;
			case 10:
				_localctx = new AddTableColumnsContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(358);
				match(ALTER);
				setState(359);
				match(TABLE);
				setState(360);
				tableIdentifier();
				setState(361);
				match(ADD);
				setState(362);
				match(COLUMNS);
				setState(363);
				match(T__0);
				setState(364);
				((AddTableColumnsContext)_localctx).columns = colTypeList();
				setState(365);
				match(T__1);
				}
				break;
			case 11:
				_localctx = new RenameTableContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(367);
				match(ALTER);
				setState(368);
				_la = _input.LA(1);
				if ( !(_la==TABLE || _la==VIEW) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(369);
				((RenameTableContext)_localctx).from = tableIdentifier();
				setState(370);
				match(RENAME);
				setState(371);
				match(TO);
				setState(372);
				((RenameTableContext)_localctx).to = tableIdentifier();
				}
				break;
			case 12:
				_localctx = new SetTablePropertiesContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(374);
				match(ALTER);
				setState(375);
				_la = _input.LA(1);
				if ( !(_la==TABLE || _la==VIEW) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(376);
				tableIdentifier();
				setState(377);
				match(SET);
				setState(378);
				match(TBLPROPERTIES);
				setState(379);
				tablePropertyList();
				}
				break;
			case 13:
				_localctx = new UnsetTablePropertiesContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(381);
				match(ALTER);
				setState(382);
				_la = _input.LA(1);
				if ( !(_la==TABLE || _la==VIEW) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(383);
				tableIdentifier();
				setState(384);
				match(UNSET);
				setState(385);
				match(TBLPROPERTIES);
				setState(388);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IF) {
					{
					setState(386);
					match(IF);
					setState(387);
					match(EXISTS);
					}
				}

				setState(390);
				tablePropertyList();
				}
				break;
			case 14:
				_localctx = new ChangeColumnContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(392);
				match(ALTER);
				setState(393);
				match(TABLE);
				setState(394);
				tableIdentifier();
				setState(396);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(395);
					partitionSpec();
					}
				}

				setState(398);
				match(CHANGE);
				setState(400);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					{
					setState(399);
					match(COLUMN);
					}
					break;
				}
				setState(402);
				identifier();
				setState(403);
				colType();
				setState(405);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FIRST || _la==AFTER) {
					{
					setState(404);
					colPosition();
					}
				}

				}
				break;
			case 15:
				_localctx = new SetTableSerDeContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(407);
				match(ALTER);
				setState(408);
				match(TABLE);
				setState(409);
				tableIdentifier();
				setState(411);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(410);
					partitionSpec();
					}
				}

				setState(413);
				match(SET);
				setState(414);
				match(SERDE);
				setState(415);
				match(STRING);
				setState(419);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(416);
					match(WITH);
					setState(417);
					match(SERDEPROPERTIES);
					setState(418);
					tablePropertyList();
					}
				}

				}
				break;
			case 16:
				_localctx = new SetTableSerDeContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(421);
				match(ALTER);
				setState(422);
				match(TABLE);
				setState(423);
				tableIdentifier();
				setState(425);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(424);
					partitionSpec();
					}
				}

				setState(427);
				match(SET);
				setState(428);
				match(SERDEPROPERTIES);
				setState(429);
				tablePropertyList();
				}
				break;
			case 17:
				_localctx = new AddTablePartitionContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(431);
				match(ALTER);
				setState(432);
				match(TABLE);
				setState(433);
				tableIdentifier();
				setState(434);
				match(ADD);
				setState(438);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IF) {
					{
					setState(435);
					match(IF);
					setState(436);
					match(NOT);
					setState(437);
					match(EXISTS);
					}
				}

				setState(441); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(440);
					partitionSpecLocation();
					}
					}
					setState(443); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==PARTITION );
				}
				break;
			case 18:
				_localctx = new AddTablePartitionContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(445);
				match(ALTER);
				setState(446);
				match(VIEW);
				setState(447);
				tableIdentifier();
				setState(448);
				match(ADD);
				setState(452);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IF) {
					{
					setState(449);
					match(IF);
					setState(450);
					match(NOT);
					setState(451);
					match(EXISTS);
					}
				}

				setState(455); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(454);
					partitionSpec();
					}
					}
					setState(457); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==PARTITION );
				}
				break;
			case 19:
				_localctx = new RenameTablePartitionContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(459);
				match(ALTER);
				setState(460);
				match(TABLE);
				setState(461);
				tableIdentifier();
				setState(462);
				((RenameTablePartitionContext)_localctx).from = partitionSpec();
				setState(463);
				match(RENAME);
				setState(464);
				match(TO);
				setState(465);
				((RenameTablePartitionContext)_localctx).to = partitionSpec();
				}
				break;
			case 20:
				_localctx = new DropTablePartitionsContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(467);
				match(ALTER);
				setState(468);
				match(TABLE);
				setState(469);
				tableIdentifier();
				setState(470);
				match(DROP);
				setState(473);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IF) {
					{
					setState(471);
					match(IF);
					setState(472);
					match(EXISTS);
					}
				}

				setState(475);
				partitionSpec();
				setState(480);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(476);
					match(T__2);
					setState(477);
					partitionSpec();
					}
					}
					setState(482);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(484);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PURGE) {
					{
					setState(483);
					match(PURGE);
					}
				}

				}
				break;
			case 21:
				_localctx = new DropTablePartitionsContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(486);
				match(ALTER);
				setState(487);
				match(VIEW);
				setState(488);
				tableIdentifier();
				setState(489);
				match(DROP);
				setState(492);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IF) {
					{
					setState(490);
					match(IF);
					setState(491);
					match(EXISTS);
					}
				}

				setState(494);
				partitionSpec();
				setState(499);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(495);
					match(T__2);
					setState(496);
					partitionSpec();
					}
					}
					setState(501);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 22:
				_localctx = new SetTableLocationContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(502);
				match(ALTER);
				setState(503);
				match(TABLE);
				setState(504);
				tableIdentifier();
				setState(506);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(505);
					partitionSpec();
					}
				}

				setState(508);
				match(SET);
				setState(509);
				locationSpec();
				}
				break;
			case 23:
				_localctx = new RecoverPartitionsContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(511);
				match(ALTER);
				setState(512);
				match(TABLE);
				setState(513);
				tableIdentifier();
				setState(514);
				match(RECOVER);
				setState(515);
				match(PARTITIONS);
				}
				break;
			case 24:
				_localctx = new DropTableContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(517);
				match(DROP);
				setState(518);
				match(TABLE);
				setState(521);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
				case 1:
					{
					setState(519);
					match(IF);
					setState(520);
					match(EXISTS);
					}
					break;
				}
				setState(523);
				tableIdentifier();
				setState(525);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PURGE) {
					{
					setState(524);
					match(PURGE);
					}
				}

				}
				break;
			case 25:
				_localctx = new DropTableContext(_localctx);
				enterOuterAlt(_localctx, 25);
				{
				setState(527);
				match(DROP);
				setState(528);
				match(VIEW);
				setState(531);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
				case 1:
					{
					setState(529);
					match(IF);
					setState(530);
					match(EXISTS);
					}
					break;
				}
				setState(533);
				tableIdentifier();
				}
				break;
			case 26:
				_localctx = new CreateViewContext(_localctx);
				enterOuterAlt(_localctx, 26);
				{
				setState(534);
				match(CREATE);
				setState(537);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OR) {
					{
					setState(535);
					match(OR);
					setState(536);
					match(REPLACE);
					}
				}

				setState(543);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==GLOBAL || _la==TEMPORARY) {
					{
					setState(540);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==GLOBAL) {
						{
						setState(539);
						match(GLOBAL);
						}
					}

					setState(542);
					match(TEMPORARY);
					}
				}

				setState(545);
				match(VIEW);
				setState(549);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(546);
					match(IF);
					setState(547);
					match(NOT);
					setState(548);
					match(EXISTS);
					}
					break;
				}
				setState(551);
				tableIdentifier();
				setState(553);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(552);
					identifierCommentList();
					}
				}

				setState(557);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(555);
					match(COMMENT);
					setState(556);
					match(STRING);
					}
				}

				setState(562);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITIONED) {
					{
					setState(559);
					match(PARTITIONED);
					setState(560);
					match(ON);
					setState(561);
					identifierList();
					}
				}

				setState(566);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TBLPROPERTIES) {
					{
					setState(564);
					match(TBLPROPERTIES);
					setState(565);
					tablePropertyList();
					}
				}

				setState(568);
				match(AS);
				setState(569);
				query();
				}
				break;
			case 27:
				_localctx = new CreateTempViewUsingContext(_localctx);
				enterOuterAlt(_localctx, 27);
				{
				setState(571);
				match(CREATE);
				setState(574);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OR) {
					{
					setState(572);
					match(OR);
					setState(573);
					match(REPLACE);
					}
				}

				setState(577);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==GLOBAL) {
					{
					setState(576);
					match(GLOBAL);
					}
				}

				setState(579);
				match(TEMPORARY);
				setState(580);
				match(VIEW);
				setState(581);
				tableIdentifier();
				setState(586);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(582);
					match(T__0);
					setState(583);
					colTypeList();
					setState(584);
					match(T__1);
					}
				}

				setState(588);
				tableProvider();
				setState(591);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OPTIONS) {
					{
					setState(589);
					match(OPTIONS);
					setState(590);
					tablePropertyList();
					}
				}

				}
				break;
			case 28:
				_localctx = new AlterViewQueryContext(_localctx);
				enterOuterAlt(_localctx, 28);
				{
				setState(593);
				match(ALTER);
				setState(594);
				match(VIEW);
				setState(595);
				tableIdentifier();
				setState(597);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(596);
					match(AS);
					}
				}

				setState(599);
				query();
				}
				break;
			case 29:
				_localctx = new CreateFunctionContext(_localctx);
				enterOuterAlt(_localctx, 29);
				{
				setState(601);
				match(CREATE);
				setState(604);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OR) {
					{
					setState(602);
					match(OR);
					setState(603);
					match(REPLACE);
					}
				}

				setState(607);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TEMPORARY) {
					{
					setState(606);
					match(TEMPORARY);
					}
				}

				setState(609);
				match(FUNCTION);
				setState(613);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
				case 1:
					{
					setState(610);
					match(IF);
					setState(611);
					match(NOT);
					setState(612);
					match(EXISTS);
					}
					break;
				}
				setState(615);
				qualifiedName();
				setState(616);
				match(AS);
				setState(617);
				((CreateFunctionContext)_localctx).className = match(STRING);
				setState(627);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==USING) {
					{
					setState(618);
					match(USING);
					setState(619);
					resource();
					setState(624);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(620);
						match(T__2);
						setState(621);
						resource();
						}
						}
						setState(626);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
				break;
			case 30:
				_localctx = new DropFunctionContext(_localctx);
				enterOuterAlt(_localctx, 30);
				{
				setState(629);
				match(DROP);
				setState(631);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TEMPORARY) {
					{
					setState(630);
					match(TEMPORARY);
					}
				}

				setState(633);
				match(FUNCTION);
				setState(636);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
				case 1:
					{
					setState(634);
					match(IF);
					setState(635);
					match(EXISTS);
					}
					break;
				}
				setState(638);
				qualifiedName();
				}
				break;
			case 31:
				_localctx = new ExplainContext(_localctx);
				enterOuterAlt(_localctx, 31);
				{
				setState(639);
				match(EXPLAIN);
				setState(641);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 98)) & ~0x3f) == 0 && ((1L << (_la - 98)) & ((1L << (LOGICAL - 98)) | (1L << (CODEGEN - 98)) | (1L << (COST - 98)))) != 0) || _la==EXTENDED || _la==FORMATTED) {
					{
					setState(640);
					_la = _input.LA(1);
					if ( !(((((_la - 98)) & ~0x3f) == 0 && ((1L << (_la - 98)) & ((1L << (LOGICAL - 98)) | (1L << (CODEGEN - 98)) | (1L << (COST - 98)))) != 0) || _la==EXTENDED || _la==FORMATTED) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(643);
				statement();
				}
				break;
			case 32:
				_localctx = new ShowTablesContext(_localctx);
				enterOuterAlt(_localctx, 32);
				{
				setState(644);
				match(SHOW);
				setState(645);
				match(TABLES);
				setState(648);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FROM || _la==IN) {
					{
					setState(646);
					_la = _input.LA(1);
					if ( !(_la==FROM || _la==IN) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(647);
					((ShowTablesContext)_localctx).db = identifier();
					}
				}

				setState(654);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LIKE || _la==STRING) {
					{
					setState(651);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==LIKE) {
						{
						setState(650);
						match(LIKE);
						}
					}

					setState(653);
					((ShowTablesContext)_localctx).pattern = match(STRING);
					}
				}

				}
				break;
			case 33:
				_localctx = new ShowTableContext(_localctx);
				enterOuterAlt(_localctx, 33);
				{
				setState(656);
				match(SHOW);
				setState(657);
				match(TABLE);
				setState(658);
				match(EXTENDED);
				setState(661);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FROM || _la==IN) {
					{
					setState(659);
					_la = _input.LA(1);
					if ( !(_la==FROM || _la==IN) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(660);
					((ShowTableContext)_localctx).db = identifier();
					}
				}

				setState(663);
				match(LIKE);
				setState(664);
				((ShowTableContext)_localctx).pattern = match(STRING);
				setState(666);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(665);
					partitionSpec();
					}
				}

				}
				break;
			case 34:
				_localctx = new ShowDatabasesContext(_localctx);
				enterOuterAlt(_localctx, 34);
				{
				setState(668);
				match(SHOW);
				setState(669);
				match(DATABASES);
				setState(674);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LIKE || _la==STRING) {
					{
					setState(671);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==LIKE) {
						{
						setState(670);
						match(LIKE);
						}
					}

					setState(673);
					((ShowDatabasesContext)_localctx).pattern = match(STRING);
					}
				}

				}
				break;
			case 35:
				_localctx = new ShowTblPropertiesContext(_localctx);
				enterOuterAlt(_localctx, 35);
				{
				setState(676);
				match(SHOW);
				setState(677);
				match(TBLPROPERTIES);
				setState(678);
				((ShowTblPropertiesContext)_localctx).table = tableIdentifier();
				setState(683);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(679);
					match(T__0);
					setState(680);
					((ShowTblPropertiesContext)_localctx).key = tablePropertyKey();
					setState(681);
					match(T__1);
					}
				}

				}
				break;
			case 36:
				_localctx = new ShowColumnsContext(_localctx);
				enterOuterAlt(_localctx, 36);
				{
				setState(685);
				match(SHOW);
				setState(686);
				match(COLUMNS);
				setState(687);
				_la = _input.LA(1);
				if ( !(_la==FROM || _la==IN) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(688);
				tableIdentifier();
				setState(691);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FROM || _la==IN) {
					{
					setState(689);
					_la = _input.LA(1);
					if ( !(_la==FROM || _la==IN) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(690);
					((ShowColumnsContext)_localctx).db = identifier();
					}
				}

				}
				break;
			case 37:
				_localctx = new ShowPartitionsContext(_localctx);
				enterOuterAlt(_localctx, 37);
				{
				setState(693);
				match(SHOW);
				setState(694);
				match(PARTITIONS);
				setState(695);
				tableIdentifier();
				setState(697);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(696);
					partitionSpec();
					}
				}

				}
				break;
			case 38:
				_localctx = new ShowFunctionsContext(_localctx);
				enterOuterAlt(_localctx, 38);
				{
				setState(699);
				match(SHOW);
				setState(701);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
				case 1:
					{
					setState(700);
					identifier();
					}
					break;
				}
				setState(703);
				match(FUNCTIONS);
				setState(711);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (SELECT - 11)) | (1L << (FROM - 11)) | (1L << (ADD - 11)) | (1L << (AS - 11)) | (1L << (ALL - 11)) | (1L << (ANY - 11)) | (1L << (DISTINCT - 11)) | (1L << (WHERE - 11)) | (1L << (GROUP - 11)) | (1L << (BY - 11)) | (1L << (GROUPING - 11)) | (1L << (SETS - 11)) | (1L << (CUBE - 11)) | (1L << (ROLLUP - 11)) | (1L << (ORDER - 11)) | (1L << (HAVING - 11)) | (1L << (LIMIT - 11)) | (1L << (AT - 11)) | (1L << (OR - 11)) | (1L << (AND - 11)) | (1L << (IN - 11)) | (1L << (NOT - 11)) | (1L << (NO - 11)) | (1L << (EXISTS - 11)) | (1L << (BETWEEN - 11)) | (1L << (LIKE - 11)) | (1L << (RLIKE - 11)) | (1L << (IS - 11)) | (1L << (NULL - 11)) | (1L << (TRUE - 11)) | (1L << (FALSE - 11)) | (1L << (NULLS - 11)) | (1L << (ASC - 11)) | (1L << (DESC - 11)) | (1L << (FOR - 11)) | (1L << (INTERVAL - 11)) | (1L << (CASE - 11)) | (1L << (WHEN - 11)) | (1L << (THEN - 11)) | (1L << (ELSE - 11)) | (1L << (END - 11)) | (1L << (JOIN - 11)) | (1L << (CROSS - 11)) | (1L << (OUTER - 11)) | (1L << (INNER - 11)) | (1L << (LEFT - 11)) | (1L << (SEMI - 11)) | (1L << (RIGHT - 11)) | (1L << (FULL - 11)) | (1L << (NATURAL - 11)) | (1L << (ON - 11)) | (1L << (PIVOT - 11)) | (1L << (LATERAL - 11)) | (1L << (WINDOW - 11)) | (1L << (OVER - 11)) | (1L << (PARTITION - 11)) | (1L << (RANGE - 11)) | (1L << (ROWS - 11)) | (1L << (UNBOUNDED - 11)) | (1L << (PRECEDING - 11)) | (1L << (FOLLOWING - 11)) | (1L << (CURRENT - 11)) | (1L << (FIRST - 11)) | (1L << (AFTER - 11)))) != 0) || ((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & ((1L << (LAST - 75)) | (1L << (ROW - 75)) | (1L << (WITH - 75)) | (1L << (VALUES - 75)) | (1L << (CREATE - 75)) | (1L << (TABLE - 75)) | (1L << (DIRECTORY - 75)) | (1L << (VIEW - 75)) | (1L << (REPLACE - 75)) | (1L << (INSERT - 75)) | (1L << (DELETE - 75)) | (1L << (INTO - 75)) | (1L << (DESCRIBE - 75)) | (1L << (EXPLAIN - 75)) | (1L << (FORMAT - 75)) | (1L << (LOGICAL - 75)) | (1L << (CODEGEN - 75)) | (1L << (COST - 75)) | (1L << (CAST - 75)) | (1L << (SHOW - 75)) | (1L << (TABLES - 75)) | (1L << (COLUMNS - 75)) | (1L << (COLUMN - 75)) | (1L << (USE - 75)) | (1L << (PARTITIONS - 75)) | (1L << (FUNCTIONS - 75)) | (1L << (DROP - 75)) | (1L << (UNION - 75)) | (1L << (EXCEPT - 75)) | (1L << (SETMINUS - 75)) | (1L << (INTERSECT - 75)) | (1L << (TO - 75)) | (1L << (TABLESAMPLE - 75)) | (1L << (STRATIFY - 75)) | (1L << (ALTER - 75)) | (1L << (RENAME - 75)) | (1L << (ARRAY - 75)) | (1L << (MAP - 75)) | (1L << (STRUCT - 75)) | (1L << (COMMENT - 75)) | (1L << (SET - 75)) | (1L << (RESET - 75)) | (1L << (DATA - 75)) | (1L << (START - 75)) | (1L << (TRANSACTION - 75)) | (1L << (COMMIT - 75)) | (1L << (ROLLBACK - 75)) | (1L << (MACRO - 75)) | (1L << (IGNORE - 75)) | (1L << (BOTH - 75)) | (1L << (LEADING - 75)) | (1L << (TRAILING - 75)) | (1L << (IF - 75)) | (1L << (POSITION - 75)) | (1L << (EXTRACT - 75)))) != 0) || ((((_la - 151)) & ~0x3f) == 0 && ((1L << (_la - 151)) & ((1L << (DIV - 151)) | (1L << (PERCENTLIT - 151)) | (1L << (BUCKET - 151)) | (1L << (OUT - 151)) | (1L << (OF - 151)) | (1L << (SORT - 151)) | (1L << (CLUSTER - 151)) | (1L << (DISTRIBUTE - 151)) | (1L << (OVERWRITE - 151)) | (1L << (TRANSFORM - 151)) | (1L << (REDUCE - 151)) | (1L << (SERDE - 151)) | (1L << (SERDEPROPERTIES - 151)) | (1L << (RECORDREADER - 151)) | (1L << (RECORDWRITER - 151)) | (1L << (DELIMITED - 151)) | (1L << (FIELDS - 151)) | (1L << (TERMINATED - 151)) | (1L << (COLLECTION - 151)) | (1L << (ITEMS - 151)) | (1L << (KEYS - 151)) | (1L << (ESCAPED - 151)) | (1L << (LINES - 151)) | (1L << (SEPARATED - 151)) | (1L << (FUNCTION - 151)) | (1L << (EXTENDED - 151)) | (1L << (REFRESH - 151)) | (1L << (CLEAR - 151)) | (1L << (CACHE - 151)) | (1L << (UNCACHE - 151)) | (1L << (LAZY - 151)) | (1L << (FORMATTED - 151)) | (1L << (GLOBAL - 151)) | (1L << (TEMPORARY - 151)) | (1L << (OPTIONS - 151)) | (1L << (UNSET - 151)) | (1L << (TBLPROPERTIES - 151)) | (1L << (DBPROPERTIES - 151)) | (1L << (BUCKETS - 151)) | (1L << (SKEWED - 151)) | (1L << (STORED - 151)) | (1L << (DIRECTORIES - 151)) | (1L << (LOCATION - 151)) | (1L << (EXCHANGE - 151)) | (1L << (ARCHIVE - 151)) | (1L << (UNARCHIVE - 151)) | (1L << (FILEFORMAT - 151)) | (1L << (TOUCH - 151)) | (1L << (COMPACT - 151)) | (1L << (CONCATENATE - 151)) | (1L << (CHANGE - 151)) | (1L << (CASCADE - 151)) | (1L << (RESTRICT - 151)) | (1L << (CLUSTERED - 151)) | (1L << (SORTED - 151)) | (1L << (PURGE - 151)) | (1L << (INPUTFORMAT - 151)) | (1L << (OUTPUTFORMAT - 151)))) != 0) || ((((_la - 215)) & ~0x3f) == 0 && ((1L << (_la - 215)) & ((1L << (DATABASE - 215)) | (1L << (DATABASES - 215)) | (1L << (DFS - 215)) | (1L << (TRUNCATE - 215)) | (1L << (ANALYZE - 215)) | (1L << (COMPUTE - 215)) | (1L << (LIST - 215)) | (1L << (STATISTICS - 215)) | (1L << (PARTITIONED - 215)) | (1L << (EXTERNAL - 215)) | (1L << (DEFINED - 215)) | (1L << (REVOKE - 215)) | (1L << (GRANT - 215)) | (1L << (LOCK - 215)) | (1L << (UNLOCK - 215)) | (1L << (MSCK - 215)) | (1L << (REPAIR - 215)) | (1L << (RECOVER - 215)) | (1L << (EXPORT - 215)) | (1L << (IMPORT - 215)) | (1L << (LOAD - 215)) | (1L << (ROLE - 215)) | (1L << (ROLES - 215)) | (1L << (COMPACTIONS - 215)) | (1L << (PRINCIPALS - 215)) | (1L << (TRANSACTIONS - 215)) | (1L << (INDEX - 215)) | (1L << (INDEXES - 215)) | (1L << (LOCKS - 215)) | (1L << (OPTION - 215)) | (1L << (ANTI - 215)) | (1L << (LOCAL - 215)) | (1L << (INPATH - 215)) | (1L << (STRING - 215)) | (1L << (IDENTIFIER - 215)) | (1L << (BACKQUOTED_IDENTIFIER - 215)))) != 0)) {
					{
					setState(705);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
					case 1:
						{
						setState(704);
						match(LIKE);
						}
						break;
					}
					setState(709);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case SELECT:
					case FROM:
					case ADD:
					case AS:
					case ALL:
					case ANY:
					case DISTINCT:
					case WHERE:
					case GROUP:
					case BY:
					case GROUPING:
					case SETS:
					case CUBE:
					case ROLLUP:
					case ORDER:
					case HAVING:
					case LIMIT:
					case AT:
					case OR:
					case AND:
					case IN:
					case NOT:
					case NO:
					case EXISTS:
					case BETWEEN:
					case LIKE:
					case RLIKE:
					case IS:
					case NULL:
					case TRUE:
					case FALSE:
					case NULLS:
					case ASC:
					case DESC:
					case FOR:
					case INTERVAL:
					case CASE:
					case WHEN:
					case THEN:
					case ELSE:
					case END:
					case JOIN:
					case CROSS:
					case OUTER:
					case INNER:
					case LEFT:
					case SEMI:
					case RIGHT:
					case FULL:
					case NATURAL:
					case ON:
					case PIVOT:
					case LATERAL:
					case WINDOW:
					case OVER:
					case PARTITION:
					case RANGE:
					case ROWS:
					case UNBOUNDED:
					case PRECEDING:
					case FOLLOWING:
					case CURRENT:
					case FIRST:
					case AFTER:
					case LAST:
					case ROW:
					case WITH:
					case VALUES:
					case CREATE:
					case TABLE:
					case DIRECTORY:
					case VIEW:
					case REPLACE:
					case INSERT:
					case DELETE:
					case INTO:
					case DESCRIBE:
					case EXPLAIN:
					case FORMAT:
					case LOGICAL:
					case CODEGEN:
					case COST:
					case CAST:
					case SHOW:
					case TABLES:
					case COLUMNS:
					case COLUMN:
					case USE:
					case PARTITIONS:
					case FUNCTIONS:
					case DROP:
					case UNION:
					case EXCEPT:
					case SETMINUS:
					case INTERSECT:
					case TO:
					case TABLESAMPLE:
					case STRATIFY:
					case ALTER:
					case RENAME:
					case ARRAY:
					case MAP:
					case STRUCT:
					case COMMENT:
					case SET:
					case RESET:
					case DATA:
					case START:
					case TRANSACTION:
					case COMMIT:
					case ROLLBACK:
					case MACRO:
					case IGNORE:
					case BOTH:
					case LEADING:
					case TRAILING:
					case IF:
					case POSITION:
					case EXTRACT:
					case DIV:
					case PERCENTLIT:
					case BUCKET:
					case OUT:
					case OF:
					case SORT:
					case CLUSTER:
					case DISTRIBUTE:
					case OVERWRITE:
					case TRANSFORM:
					case REDUCE:
					case SERDE:
					case SERDEPROPERTIES:
					case RECORDREADER:
					case RECORDWRITER:
					case DELIMITED:
					case FIELDS:
					case TERMINATED:
					case COLLECTION:
					case ITEMS:
					case KEYS:
					case ESCAPED:
					case LINES:
					case SEPARATED:
					case FUNCTION:
					case EXTENDED:
					case REFRESH:
					case CLEAR:
					case CACHE:
					case UNCACHE:
					case LAZY:
					case FORMATTED:
					case GLOBAL:
					case TEMPORARY:
					case OPTIONS:
					case UNSET:
					case TBLPROPERTIES:
					case DBPROPERTIES:
					case BUCKETS:
					case SKEWED:
					case STORED:
					case DIRECTORIES:
					case LOCATION:
					case EXCHANGE:
					case ARCHIVE:
					case UNARCHIVE:
					case FILEFORMAT:
					case TOUCH:
					case COMPACT:
					case CONCATENATE:
					case CHANGE:
					case CASCADE:
					case RESTRICT:
					case CLUSTERED:
					case SORTED:
					case PURGE:
					case INPUTFORMAT:
					case OUTPUTFORMAT:
					case DATABASE:
					case DATABASES:
					case DFS:
					case TRUNCATE:
					case ANALYZE:
					case COMPUTE:
					case LIST:
					case STATISTICS:
					case PARTITIONED:
					case EXTERNAL:
					case DEFINED:
					case REVOKE:
					case GRANT:
					case LOCK:
					case UNLOCK:
					case MSCK:
					case REPAIR:
					case RECOVER:
					case EXPORT:
					case IMPORT:
					case LOAD:
					case ROLE:
					case ROLES:
					case COMPACTIONS:
					case PRINCIPALS:
					case TRANSACTIONS:
					case INDEX:
					case INDEXES:
					case LOCKS:
					case OPTION:
					case ANTI:
					case LOCAL:
					case INPATH:
					case IDENTIFIER:
					case BACKQUOTED_IDENTIFIER:
						{
						setState(707);
						qualifiedName();
						}
						break;
					case STRING:
						{
						setState(708);
						((ShowFunctionsContext)_localctx).pattern = match(STRING);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
				}

				}
				break;
			case 39:
				_localctx = new ShowCreateTableContext(_localctx);
				enterOuterAlt(_localctx, 39);
				{
				setState(713);
				match(SHOW);
				setState(714);
				match(CREATE);
				setState(715);
				match(TABLE);
				setState(716);
				tableIdentifier();
				}
				break;
			case 40:
				_localctx = new DescribeFunctionContext(_localctx);
				enterOuterAlt(_localctx, 40);
				{
				setState(717);
				_la = _input.LA(1);
				if ( !(_la==DESC || _la==DESCRIBE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(718);
				match(FUNCTION);
				setState(720);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
				case 1:
					{
					setState(719);
					match(EXTENDED);
					}
					break;
				}
				setState(722);
				describeFuncName();
				}
				break;
			case 41:
				_localctx = new DescribeDatabaseContext(_localctx);
				enterOuterAlt(_localctx, 41);
				{
				setState(723);
				_la = _input.LA(1);
				if ( !(_la==DESC || _la==DESCRIBE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(724);
				match(DATABASE);
				setState(726);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
				case 1:
					{
					setState(725);
					match(EXTENDED);
					}
					break;
				}
				setState(728);
				identifier();
				}
				break;
			case 42:
				_localctx = new DescribeTableContext(_localctx);
				enterOuterAlt(_localctx, 42);
				{
				setState(729);
				_la = _input.LA(1);
				if ( !(_la==DESC || _la==DESCRIBE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(731);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
				case 1:
					{
					setState(730);
					match(TABLE);
					}
					break;
				}
				setState(734);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
				case 1:
					{
					setState(733);
					((DescribeTableContext)_localctx).option = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==EXTENDED || _la==FORMATTED) ) {
						((DescribeTableContext)_localctx).option = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				setState(736);
				tableIdentifier();
				setState(738);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
				case 1:
					{
					setState(737);
					partitionSpec();
					}
					break;
				}
				setState(741);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (SELECT - 11)) | (1L << (FROM - 11)) | (1L << (ADD - 11)) | (1L << (AS - 11)) | (1L << (ALL - 11)) | (1L << (ANY - 11)) | (1L << (DISTINCT - 11)) | (1L << (WHERE - 11)) | (1L << (GROUP - 11)) | (1L << (BY - 11)) | (1L << (GROUPING - 11)) | (1L << (SETS - 11)) | (1L << (CUBE - 11)) | (1L << (ROLLUP - 11)) | (1L << (ORDER - 11)) | (1L << (HAVING - 11)) | (1L << (LIMIT - 11)) | (1L << (AT - 11)) | (1L << (OR - 11)) | (1L << (AND - 11)) | (1L << (IN - 11)) | (1L << (NOT - 11)) | (1L << (NO - 11)) | (1L << (EXISTS - 11)) | (1L << (BETWEEN - 11)) | (1L << (LIKE - 11)) | (1L << (RLIKE - 11)) | (1L << (IS - 11)) | (1L << (NULL - 11)) | (1L << (TRUE - 11)) | (1L << (FALSE - 11)) | (1L << (NULLS - 11)) | (1L << (ASC - 11)) | (1L << (DESC - 11)) | (1L << (FOR - 11)) | (1L << (INTERVAL - 11)) | (1L << (CASE - 11)) | (1L << (WHEN - 11)) | (1L << (THEN - 11)) | (1L << (ELSE - 11)) | (1L << (END - 11)) | (1L << (JOIN - 11)) | (1L << (CROSS - 11)) | (1L << (OUTER - 11)) | (1L << (INNER - 11)) | (1L << (LEFT - 11)) | (1L << (SEMI - 11)) | (1L << (RIGHT - 11)) | (1L << (FULL - 11)) | (1L << (NATURAL - 11)) | (1L << (ON - 11)) | (1L << (PIVOT - 11)) | (1L << (LATERAL - 11)) | (1L << (WINDOW - 11)) | (1L << (OVER - 11)) | (1L << (PARTITION - 11)) | (1L << (RANGE - 11)) | (1L << (ROWS - 11)) | (1L << (UNBOUNDED - 11)) | (1L << (PRECEDING - 11)) | (1L << (FOLLOWING - 11)) | (1L << (CURRENT - 11)) | (1L << (FIRST - 11)) | (1L << (AFTER - 11)))) != 0) || ((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & ((1L << (LAST - 75)) | (1L << (ROW - 75)) | (1L << (WITH - 75)) | (1L << (VALUES - 75)) | (1L << (CREATE - 75)) | (1L << (TABLE - 75)) | (1L << (DIRECTORY - 75)) | (1L << (VIEW - 75)) | (1L << (REPLACE - 75)) | (1L << (INSERT - 75)) | (1L << (DELETE - 75)) | (1L << (INTO - 75)) | (1L << (DESCRIBE - 75)) | (1L << (EXPLAIN - 75)) | (1L << (FORMAT - 75)) | (1L << (LOGICAL - 75)) | (1L << (CODEGEN - 75)) | (1L << (COST - 75)) | (1L << (CAST - 75)) | (1L << (SHOW - 75)) | (1L << (TABLES - 75)) | (1L << (COLUMNS - 75)) | (1L << (COLUMN - 75)) | (1L << (USE - 75)) | (1L << (PARTITIONS - 75)) | (1L << (FUNCTIONS - 75)) | (1L << (DROP - 75)) | (1L << (UNION - 75)) | (1L << (EXCEPT - 75)) | (1L << (SETMINUS - 75)) | (1L << (INTERSECT - 75)) | (1L << (TO - 75)) | (1L << (TABLESAMPLE - 75)) | (1L << (STRATIFY - 75)) | (1L << (ALTER - 75)) | (1L << (RENAME - 75)) | (1L << (ARRAY - 75)) | (1L << (MAP - 75)) | (1L << (STRUCT - 75)) | (1L << (COMMENT - 75)) | (1L << (SET - 75)) | (1L << (RESET - 75)) | (1L << (DATA - 75)) | (1L << (START - 75)) | (1L << (TRANSACTION - 75)) | (1L << (COMMIT - 75)) | (1L << (ROLLBACK - 75)) | (1L << (MACRO - 75)) | (1L << (IGNORE - 75)) | (1L << (BOTH - 75)) | (1L << (LEADING - 75)) | (1L << (TRAILING - 75)) | (1L << (IF - 75)) | (1L << (POSITION - 75)) | (1L << (EXTRACT - 75)))) != 0) || ((((_la - 151)) & ~0x3f) == 0 && ((1L << (_la - 151)) & ((1L << (DIV - 151)) | (1L << (PERCENTLIT - 151)) | (1L << (BUCKET - 151)) | (1L << (OUT - 151)) | (1L << (OF - 151)) | (1L << (SORT - 151)) | (1L << (CLUSTER - 151)) | (1L << (DISTRIBUTE - 151)) | (1L << (OVERWRITE - 151)) | (1L << (TRANSFORM - 151)) | (1L << (REDUCE - 151)) | (1L << (SERDE - 151)) | (1L << (SERDEPROPERTIES - 151)) | (1L << (RECORDREADER - 151)) | (1L << (RECORDWRITER - 151)) | (1L << (DELIMITED - 151)) | (1L << (FIELDS - 151)) | (1L << (TERMINATED - 151)) | (1L << (COLLECTION - 151)) | (1L << (ITEMS - 151)) | (1L << (KEYS - 151)) | (1L << (ESCAPED - 151)) | (1L << (LINES - 151)) | (1L << (SEPARATED - 151)) | (1L << (FUNCTION - 151)) | (1L << (EXTENDED - 151)) | (1L << (REFRESH - 151)) | (1L << (CLEAR - 151)) | (1L << (CACHE - 151)) | (1L << (UNCACHE - 151)) | (1L << (LAZY - 151)) | (1L << (FORMATTED - 151)) | (1L << (GLOBAL - 151)) | (1L << (TEMPORARY - 151)) | (1L << (OPTIONS - 151)) | (1L << (UNSET - 151)) | (1L << (TBLPROPERTIES - 151)) | (1L << (DBPROPERTIES - 151)) | (1L << (BUCKETS - 151)) | (1L << (SKEWED - 151)) | (1L << (STORED - 151)) | (1L << (DIRECTORIES - 151)) | (1L << (LOCATION - 151)) | (1L << (EXCHANGE - 151)) | (1L << (ARCHIVE - 151)) | (1L << (UNARCHIVE - 151)) | (1L << (FILEFORMAT - 151)) | (1L << (TOUCH - 151)) | (1L << (COMPACT - 151)) | (1L << (CONCATENATE - 151)) | (1L << (CHANGE - 151)) | (1L << (CASCADE - 151)) | (1L << (RESTRICT - 151)) | (1L << (CLUSTERED - 151)) | (1L << (SORTED - 151)) | (1L << (PURGE - 151)) | (1L << (INPUTFORMAT - 151)) | (1L << (OUTPUTFORMAT - 151)))) != 0) || ((((_la - 215)) & ~0x3f) == 0 && ((1L << (_la - 215)) & ((1L << (DATABASE - 215)) | (1L << (DATABASES - 215)) | (1L << (DFS - 215)) | (1L << (TRUNCATE - 215)) | (1L << (ANALYZE - 215)) | (1L << (COMPUTE - 215)) | (1L << (LIST - 215)) | (1L << (STATISTICS - 215)) | (1L << (PARTITIONED - 215)) | (1L << (EXTERNAL - 215)) | (1L << (DEFINED - 215)) | (1L << (REVOKE - 215)) | (1L << (GRANT - 215)) | (1L << (LOCK - 215)) | (1L << (UNLOCK - 215)) | (1L << (MSCK - 215)) | (1L << (REPAIR - 215)) | (1L << (RECOVER - 215)) | (1L << (EXPORT - 215)) | (1L << (IMPORT - 215)) | (1L << (LOAD - 215)) | (1L << (ROLE - 215)) | (1L << (ROLES - 215)) | (1L << (COMPACTIONS - 215)) | (1L << (PRINCIPALS - 215)) | (1L << (TRANSACTIONS - 215)) | (1L << (INDEX - 215)) | (1L << (INDEXES - 215)) | (1L << (LOCKS - 215)) | (1L << (OPTION - 215)) | (1L << (ANTI - 215)) | (1L << (LOCAL - 215)) | (1L << (INPATH - 215)) | (1L << (IDENTIFIER - 215)) | (1L << (BACKQUOTED_IDENTIFIER - 215)))) != 0)) {
					{
					setState(740);
					describeColName();
					}
				}

				}
				break;
			case 43:
				_localctx = new RefreshTableContext(_localctx);
				enterOuterAlt(_localctx, 43);
				{
				setState(743);
				match(REFRESH);
				setState(744);
				match(TABLE);
				setState(745);
				tableIdentifier();
				}
				break;
			case 44:
				_localctx = new RefreshResourceContext(_localctx);
				enterOuterAlt(_localctx, 44);
				{
				setState(746);
				match(REFRESH);
				setState(754);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
				case 1:
					{
					setState(747);
					match(STRING);
					}
					break;
				case 2:
					{
					setState(751);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
					while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1+1 ) {
							{
							{
							setState(748);
							matchWildcard();
							}
							} 
						}
						setState(753);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
					}
					}
					break;
				}
				}
				break;
			case 45:
				_localctx = new CacheTableContext(_localctx);
				enterOuterAlt(_localctx, 45);
				{
				setState(756);
				match(CACHE);
				setState(758);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LAZY) {
					{
					setState(757);
					match(LAZY);
					}
				}

				setState(760);
				match(TABLE);
				setState(761);
				tableIdentifier();
				setState(766);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << AS))) != 0) || ((((_la - 77)) & ~0x3f) == 0 && ((1L << (_la - 77)) & ((1L << (WITH - 77)) | (1L << (VALUES - 77)) | (1L << (TABLE - 77)) | (1L << (INSERT - 77)) | (1L << (MAP - 77)))) != 0) || _la==REDUCE) {
					{
					setState(763);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(762);
						match(AS);
						}
					}

					setState(765);
					query();
					}
				}

				}
				break;
			case 46:
				_localctx = new UncacheTableContext(_localctx);
				enterOuterAlt(_localctx, 46);
				{
				setState(768);
				match(UNCACHE);
				setState(769);
				match(TABLE);
				setState(772);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
				case 1:
					{
					setState(770);
					match(IF);
					setState(771);
					match(EXISTS);
					}
					break;
				}
				setState(774);
				tableIdentifier();
				}
				break;
			case 47:
				_localctx = new ClearCacheContext(_localctx);
				enterOuterAlt(_localctx, 47);
				{
				setState(775);
				match(CLEAR);
				setState(776);
				match(CACHE);
				}
				break;
			case 48:
				_localctx = new LoadDataContext(_localctx);
				enterOuterAlt(_localctx, 48);
				{
				setState(777);
				match(LOAD);
				setState(778);
				match(DATA);
				setState(780);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LOCAL) {
					{
					setState(779);
					match(LOCAL);
					}
				}

				setState(782);
				match(INPATH);
				setState(783);
				((LoadDataContext)_localctx).path = match(STRING);
				setState(785);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OVERWRITE) {
					{
					setState(784);
					match(OVERWRITE);
					}
				}

				setState(787);
				match(INTO);
				setState(788);
				match(TABLE);
				setState(789);
				tableIdentifier();
				setState(791);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(790);
					partitionSpec();
					}
				}

				}
				break;
			case 49:
				_localctx = new TruncateTableContext(_localctx);
				enterOuterAlt(_localctx, 49);
				{
				setState(793);
				match(TRUNCATE);
				setState(794);
				match(TABLE);
				setState(795);
				tableIdentifier();
				setState(797);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(796);
					partitionSpec();
					}
				}

				}
				break;
			case 50:
				_localctx = new RepairTableContext(_localctx);
				enterOuterAlt(_localctx, 50);
				{
				setState(799);
				match(MSCK);
				setState(800);
				match(REPAIR);
				setState(801);
				match(TABLE);
				setState(802);
				tableIdentifier();
				}
				break;
			case 51:
				_localctx = new ManageResourceContext(_localctx);
				enterOuterAlt(_localctx, 51);
				{
				setState(803);
				((ManageResourceContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==LIST) ) {
					((ManageResourceContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(804);
				identifier();
				setState(808);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(805);
						matchWildcard();
						}
						} 
					}
					setState(810);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
				}
				}
				break;
			case 52:
				_localctx = new FailNativeCommandContext(_localctx);
				enterOuterAlt(_localctx, 52);
				{
				setState(811);
				match(SET);
				setState(812);
				match(ROLE);
				setState(816);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(813);
						matchWildcard();
						}
						} 
					}
					setState(818);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
				}
				}
				break;
			case 53:
				_localctx = new SetConfigurationContext(_localctx);
				enterOuterAlt(_localctx, 53);
				{
				setState(819);
				match(SET);
				setState(823);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(820);
						matchWildcard();
						}
						} 
					}
					setState(825);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
				}
				}
				break;
			case 54:
				_localctx = new ResetConfigurationContext(_localctx);
				enterOuterAlt(_localctx, 54);
				{
				setState(826);
				match(RESET);
				}
				break;
			case 55:
				_localctx = new CreateIndexCommandContext(_localctx);
				enterOuterAlt(_localctx, 55);
				{
				setState(827);
				match(CREATE);
				setState(828);
				match(INDEX);
				setState(829);
				identifier();
				setState(830);
				match(ON);
				setState(831);
				relation();
				setState(832);
				match(USE);
				setState(833);
				indexType();
				}
				break;
			case 56:
				_localctx = new ShowIndexOnRelationContext(_localctx);
				enterOuterAlt(_localctx, 56);
				{
				setState(835);
				match(SHOW);
				setState(836);
				match(INDEX);
				setState(837);
				match(FROM);
				setState(838);
				relation();
				}
				break;
			case 57:
				_localctx = new DropIndexOnRelationContext(_localctx);
				enterOuterAlt(_localctx, 57);
				{
				setState(839);
				match(DROP);
				setState(840);
				match(INDEX);
				setState(841);
				identifier();
				setState(842);
				match(ON);
				setState(843);
				relation();
				}
				break;
			case 58:
				_localctx = new FailNativeCommandContext(_localctx);
				enterOuterAlt(_localctx, 58);
				{
				setState(845);
				unsupportedHiveNativeCommands();
				setState(849);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(846);
						matchWildcard();
						}
						} 
					}
					setState(851);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexTypeContext extends ParserRuleContext {
		public TerminalNode RTREE() { return getToken(NewSqlBaseParser.RTREE, 0); }
		public TerminalNode HASHMAP() { return getToken(NewSqlBaseParser.HASHMAP, 0); }
		public TerminalNode TREEMAP() { return getToken(NewSqlBaseParser.TREEMAP, 0); }
		public IndexTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIndexType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIndexType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIndexType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexTypeContext indexType() throws RecognitionException {
		IndexTypeContext _localctx = new IndexTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_indexType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(854);
			_la = _input.LA(1);
			if ( !(((((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & ((1L << (RTREE - 82)) | (1L << (HASHMAP - 82)) | (1L << (TREEMAP - 82)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnsupportedHiveNativeCommandsContext extends ParserRuleContext {
		public Token kw1;
		public Token kw2;
		public Token kw3;
		public Token kw4;
		public Token kw5;
		public Token kw6;
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode ROLE() { return getToken(NewSqlBaseParser.ROLE, 0); }
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public TerminalNode GRANT() { return getToken(NewSqlBaseParser.GRANT, 0); }
		public TerminalNode REVOKE() { return getToken(NewSqlBaseParser.REVOKE, 0); }
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode PRINCIPALS() { return getToken(NewSqlBaseParser.PRINCIPALS, 0); }
		public TerminalNode ROLES() { return getToken(NewSqlBaseParser.ROLES, 0); }
		public TerminalNode CURRENT() { return getToken(NewSqlBaseParser.CURRENT, 0); }
		public TerminalNode EXPORT() { return getToken(NewSqlBaseParser.EXPORT, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode IMPORT() { return getToken(NewSqlBaseParser.IMPORT, 0); }
		public TerminalNode COMPACTIONS() { return getToken(NewSqlBaseParser.COMPACTIONS, 0); }
		public TerminalNode TRANSACTIONS() { return getToken(NewSqlBaseParser.TRANSACTIONS, 0); }
		public TerminalNode LOCKS() { return getToken(NewSqlBaseParser.LOCKS, 0); }
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode INDEX() { return getToken(NewSqlBaseParser.INDEX, 0); }
		public TerminalNode LOCK() { return getToken(NewSqlBaseParser.LOCK, 0); }
		public TerminalNode DATABASE() { return getToken(NewSqlBaseParser.DATABASE, 0); }
		public TerminalNode UNLOCK() { return getToken(NewSqlBaseParser.UNLOCK, 0); }
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode MACRO() { return getToken(NewSqlBaseParser.MACRO, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode CLUSTERED() { return getToken(NewSqlBaseParser.CLUSTERED, 0); }
		public TerminalNode BY() { return getToken(NewSqlBaseParser.BY, 0); }
		public TerminalNode SORTED() { return getToken(NewSqlBaseParser.SORTED, 0); }
		public TerminalNode SKEWED() { return getToken(NewSqlBaseParser.SKEWED, 0); }
		public TerminalNode STORED() { return getToken(NewSqlBaseParser.STORED, 0); }
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public TerminalNode DIRECTORIES() { return getToken(NewSqlBaseParser.DIRECTORIES, 0); }
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public TerminalNode LOCATION() { return getToken(NewSqlBaseParser.LOCATION, 0); }
		public TerminalNode EXCHANGE() { return getToken(NewSqlBaseParser.EXCHANGE, 0); }
		public TerminalNode PARTITION() { return getToken(NewSqlBaseParser.PARTITION, 0); }
		public TerminalNode ARCHIVE() { return getToken(NewSqlBaseParser.ARCHIVE, 0); }
		public TerminalNode UNARCHIVE() { return getToken(NewSqlBaseParser.UNARCHIVE, 0); }
		public TerminalNode TOUCH() { return getToken(NewSqlBaseParser.TOUCH, 0); }
		public TerminalNode COMPACT() { return getToken(NewSqlBaseParser.COMPACT, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TerminalNode CONCATENATE() { return getToken(NewSqlBaseParser.CONCATENATE, 0); }
		public TerminalNode FILEFORMAT() { return getToken(NewSqlBaseParser.FILEFORMAT, 0); }
		public TerminalNode REPLACE() { return getToken(NewSqlBaseParser.REPLACE, 0); }
		public TerminalNode COLUMNS() { return getToken(NewSqlBaseParser.COLUMNS, 0); }
		public TerminalNode START() { return getToken(NewSqlBaseParser.START, 0); }
		public TerminalNode TRANSACTION() { return getToken(NewSqlBaseParser.TRANSACTION, 0); }
		public TerminalNode COMMIT() { return getToken(NewSqlBaseParser.COMMIT, 0); }
		public TerminalNode ROLLBACK() { return getToken(NewSqlBaseParser.ROLLBACK, 0); }
		public TerminalNode DFS() { return getToken(NewSqlBaseParser.DFS, 0); }
		public TerminalNode DELETE() { return getToken(NewSqlBaseParser.DELETE, 0); }
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public UnsupportedHiveNativeCommandsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsupportedHiveNativeCommands; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterUnsupportedHiveNativeCommands(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitUnsupportedHiveNativeCommands(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitUnsupportedHiveNativeCommands(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnsupportedHiveNativeCommandsContext unsupportedHiveNativeCommands() throws RecognitionException {
		UnsupportedHiveNativeCommandsContext _localctx = new UnsupportedHiveNativeCommandsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_unsupportedHiveNativeCommands);
		int _la;
		try {
			setState(1020);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(856);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(CREATE);
				setState(857);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(ROLE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(858);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(DROP);
				setState(859);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(ROLE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(860);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(GRANT);
				setState(862);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
				case 1:
					{
					setState(861);
					((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(ROLE);
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(864);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(REVOKE);
				setState(866);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
				case 1:
					{
					setState(865);
					((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(ROLE);
					}
					break;
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(868);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(869);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(GRANT);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(870);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(871);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(ROLE);
				setState(873);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
				case 1:
					{
					setState(872);
					((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(GRANT);
					}
					break;
				}
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(875);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(876);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(PRINCIPALS);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(877);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(878);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(ROLES);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(879);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(880);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(CURRENT);
				setState(881);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(ROLES);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(882);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(EXPORT);
				setState(883);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(884);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(IMPORT);
				setState(885);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(886);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(887);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(COMPACTIONS);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(888);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(889);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(CREATE);
				setState(890);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(TABLE);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(891);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(892);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TRANSACTIONS);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(893);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(SHOW);
				setState(894);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(LOCKS);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(895);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(896);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(INDEX);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(897);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(LOCK);
				setState(898);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(899);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(LOCK);
				setState(900);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(DATABASE);
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(901);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(UNLOCK);
				setState(902);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(903);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(UNLOCK);
				setState(904);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(DATABASE);
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(905);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(CREATE);
				setState(906);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TEMPORARY);
				setState(907);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(MACRO);
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(908);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(DROP);
				setState(909);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TEMPORARY);
				setState(910);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(MACRO);
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(911);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(912);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(913);
				tableIdentifier();
				setState(914);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(NOT);
				setState(915);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(CLUSTERED);
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(917);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(918);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(919);
				tableIdentifier();
				setState(920);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(CLUSTERED);
				setState(921);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(BY);
				}
				break;
			case 25:
				enterOuterAlt(_localctx, 25);
				{
				setState(923);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(924);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(925);
				tableIdentifier();
				setState(926);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(NOT);
				setState(927);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(SORTED);
				}
				break;
			case 26:
				enterOuterAlt(_localctx, 26);
				{
				setState(929);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(930);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(931);
				tableIdentifier();
				setState(932);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(SKEWED);
				setState(933);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(BY);
				}
				break;
			case 27:
				enterOuterAlt(_localctx, 27);
				{
				setState(935);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(936);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(937);
				tableIdentifier();
				setState(938);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(NOT);
				setState(939);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(SKEWED);
				}
				break;
			case 28:
				enterOuterAlt(_localctx, 28);
				{
				setState(941);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(942);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(943);
				tableIdentifier();
				setState(944);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(NOT);
				setState(945);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(STORED);
				setState(946);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw5 = match(AS);
				setState(947);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw6 = match(DIRECTORIES);
				}
				break;
			case 29:
				enterOuterAlt(_localctx, 29);
				{
				setState(949);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(950);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(951);
				tableIdentifier();
				setState(952);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(SET);
				setState(953);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(SKEWED);
				setState(954);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw5 = match(LOCATION);
				}
				break;
			case 30:
				enterOuterAlt(_localctx, 30);
				{
				setState(956);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(957);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(958);
				tableIdentifier();
				setState(959);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(EXCHANGE);
				setState(960);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(PARTITION);
				}
				break;
			case 31:
				enterOuterAlt(_localctx, 31);
				{
				setState(962);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(963);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(964);
				tableIdentifier();
				setState(965);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(ARCHIVE);
				setState(966);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(PARTITION);
				}
				break;
			case 32:
				enterOuterAlt(_localctx, 32);
				{
				setState(968);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(969);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(970);
				tableIdentifier();
				setState(971);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(UNARCHIVE);
				setState(972);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(PARTITION);
				}
				break;
			case 33:
				enterOuterAlt(_localctx, 33);
				{
				setState(974);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(975);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(976);
				tableIdentifier();
				setState(977);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(TOUCH);
				}
				break;
			case 34:
				enterOuterAlt(_localctx, 34);
				{
				setState(979);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(980);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(981);
				tableIdentifier();
				setState(983);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(982);
					partitionSpec();
					}
				}

				setState(985);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(COMPACT);
				}
				break;
			case 35:
				enterOuterAlt(_localctx, 35);
				{
				setState(987);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(988);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(989);
				tableIdentifier();
				setState(991);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(990);
					partitionSpec();
					}
				}

				setState(993);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(CONCATENATE);
				}
				break;
			case 36:
				enterOuterAlt(_localctx, 36);
				{
				setState(995);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(996);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(997);
				tableIdentifier();
				setState(999);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(998);
					partitionSpec();
					}
				}

				setState(1001);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(SET);
				setState(1002);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(FILEFORMAT);
				}
				break;
			case 37:
				enterOuterAlt(_localctx, 37);
				{
				setState(1004);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ALTER);
				setState(1005);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TABLE);
				setState(1006);
				tableIdentifier();
				setState(1008);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(1007);
					partitionSpec();
					}
				}

				setState(1010);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw3 = match(REPLACE);
				setState(1011);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw4 = match(COLUMNS);
				}
				break;
			case 38:
				enterOuterAlt(_localctx, 38);
				{
				setState(1013);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(START);
				setState(1014);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(TRANSACTION);
				}
				break;
			case 39:
				enterOuterAlt(_localctx, 39);
				{
				setState(1015);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(COMMIT);
				}
				break;
			case 40:
				enterOuterAlt(_localctx, 40);
				{
				setState(1016);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(ROLLBACK);
				}
				break;
			case 41:
				enterOuterAlt(_localctx, 41);
				{
				setState(1017);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(DFS);
				}
				break;
			case 42:
				enterOuterAlt(_localctx, 42);
				{
				setState(1018);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw1 = match(DELETE);
				setState(1019);
				((UnsupportedHiveNativeCommandsContext)_localctx).kw2 = match(FROM);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateTableHeaderContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode EXTERNAL() { return getToken(NewSqlBaseParser.EXTERNAL, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public CreateTableHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTableHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateTableHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateTableHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateTableHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableHeaderContext createTableHeader() throws RecognitionException {
		CreateTableHeaderContext _localctx = new CreateTableHeaderContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_createTableHeader);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1022);
			match(CREATE);
			setState(1024);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TEMPORARY) {
				{
				setState(1023);
				match(TEMPORARY);
				}
			}

			setState(1027);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTERNAL) {
				{
				setState(1026);
				match(EXTERNAL);
				}
			}

			setState(1029);
			match(TABLE);
			setState(1033);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,106,_ctx) ) {
			case 1:
				{
				setState(1030);
				match(IF);
				setState(1031);
				match(NOT);
				setState(1032);
				match(EXISTS);
				}
				break;
			}
			setState(1035);
			tableIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BucketSpecContext extends ParserRuleContext {
		public TerminalNode CLUSTERED() { return getToken(NewSqlBaseParser.CLUSTERED, 0); }
		public List<TerminalNode> BY() { return getTokens(NewSqlBaseParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(NewSqlBaseParser.BY, i);
		}
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode INTO() { return getToken(NewSqlBaseParser.INTO, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(NewSqlBaseParser.INTEGER_VALUE, 0); }
		public TerminalNode BUCKETS() { return getToken(NewSqlBaseParser.BUCKETS, 0); }
		public TerminalNode SORTED() { return getToken(NewSqlBaseParser.SORTED, 0); }
		public OrderedIdentifierListContext orderedIdentifierList() {
			return getRuleContext(OrderedIdentifierListContext.class,0);
		}
		public BucketSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bucketSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterBucketSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitBucketSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitBucketSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BucketSpecContext bucketSpec() throws RecognitionException {
		BucketSpecContext _localctx = new BucketSpecContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_bucketSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1037);
			match(CLUSTERED);
			setState(1038);
			match(BY);
			setState(1039);
			identifierList();
			setState(1043);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SORTED) {
				{
				setState(1040);
				match(SORTED);
				setState(1041);
				match(BY);
				setState(1042);
				orderedIdentifierList();
				}
			}

			setState(1045);
			match(INTO);
			setState(1046);
			match(INTEGER_VALUE);
			setState(1047);
			match(BUCKETS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SkewSpecContext extends ParserRuleContext {
		public TerminalNode SKEWED() { return getToken(NewSqlBaseParser.SKEWED, 0); }
		public TerminalNode BY() { return getToken(NewSqlBaseParser.BY, 0); }
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public ConstantListContext constantList() {
			return getRuleContext(ConstantListContext.class,0);
		}
		public NestedConstantListContext nestedConstantList() {
			return getRuleContext(NestedConstantListContext.class,0);
		}
		public TerminalNode STORED() { return getToken(NewSqlBaseParser.STORED, 0); }
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public TerminalNode DIRECTORIES() { return getToken(NewSqlBaseParser.DIRECTORIES, 0); }
		public SkewSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skewSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSkewSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSkewSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSkewSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SkewSpecContext skewSpec() throws RecognitionException {
		SkewSpecContext _localctx = new SkewSpecContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_skewSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1049);
			match(SKEWED);
			setState(1050);
			match(BY);
			setState(1051);
			identifierList();
			setState(1052);
			match(ON);
			setState(1055);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				{
				setState(1053);
				constantList();
				}
				break;
			case 2:
				{
				setState(1054);
				nestedConstantList();
				}
				break;
			}
			setState(1060);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				{
				setState(1057);
				match(STORED);
				setState(1058);
				match(AS);
				setState(1059);
				match(DIRECTORIES);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocationSpecContext extends ParserRuleContext {
		public TerminalNode LOCATION() { return getToken(NewSqlBaseParser.LOCATION, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public LocationSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_locationSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLocationSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLocationSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLocationSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocationSpecContext locationSpec() throws RecognitionException {
		LocationSpecContext _localctx = new LocationSpecContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_locationSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1062);
			match(LOCATION);
			setState(1063);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public QueryNoWithContext queryNoWith() {
			return getRuleContext(QueryNoWithContext.class,0);
		}
		public CtesContext ctes() {
			return getRuleContext(CtesContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1066);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(1065);
				ctes();
				}
			}

			setState(1068);
			queryNoWith();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InsertIntoContext extends ParserRuleContext {
		public InsertIntoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertInto; }
	 
		public InsertIntoContext() { }
		public void copyFrom(InsertIntoContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InsertOverwriteHiveDirContext extends InsertIntoContext {
		public Token path;
		public TerminalNode INSERT() { return getToken(NewSqlBaseParser.INSERT, 0); }
		public TerminalNode OVERWRITE() { return getToken(NewSqlBaseParser.OVERWRITE, 0); }
		public TerminalNode DIRECTORY() { return getToken(NewSqlBaseParser.DIRECTORY, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode LOCAL() { return getToken(NewSqlBaseParser.LOCAL, 0); }
		public RowFormatContext rowFormat() {
			return getRuleContext(RowFormatContext.class,0);
		}
		public CreateFileFormatContext createFileFormat() {
			return getRuleContext(CreateFileFormatContext.class,0);
		}
		public InsertOverwriteHiveDirContext(InsertIntoContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInsertOverwriteHiveDir(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInsertOverwriteHiveDir(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInsertOverwriteHiveDir(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InsertOverwriteDirContext extends InsertIntoContext {
		public Token path;
		public TablePropertyListContext options;
		public TerminalNode INSERT() { return getToken(NewSqlBaseParser.INSERT, 0); }
		public TerminalNode OVERWRITE() { return getToken(NewSqlBaseParser.OVERWRITE, 0); }
		public TerminalNode DIRECTORY() { return getToken(NewSqlBaseParser.DIRECTORY, 0); }
		public TableProviderContext tableProvider() {
			return getRuleContext(TableProviderContext.class,0);
		}
		public TerminalNode LOCAL() { return getToken(NewSqlBaseParser.LOCAL, 0); }
		public TerminalNode OPTIONS() { return getToken(NewSqlBaseParser.OPTIONS, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public InsertOverwriteDirContext(InsertIntoContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInsertOverwriteDir(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInsertOverwriteDir(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInsertOverwriteDir(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InsertOverwriteTableContext extends InsertIntoContext {
		public TerminalNode INSERT() { return getToken(NewSqlBaseParser.INSERT, 0); }
		public TerminalNode OVERWRITE() { return getToken(NewSqlBaseParser.OVERWRITE, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public InsertOverwriteTableContext(InsertIntoContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInsertOverwriteTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInsertOverwriteTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInsertOverwriteTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InsertIntoTableContext extends InsertIntoContext {
		public TerminalNode INSERT() { return getToken(NewSqlBaseParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(NewSqlBaseParser.INTO, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public InsertIntoTableContext(InsertIntoContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInsertIntoTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInsertIntoTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInsertIntoTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertIntoContext insertInto() throws RecognitionException {
		InsertIntoContext _localctx = new InsertIntoContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_insertInto);
		int _la;
		try {
			setState(1118);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,121,_ctx) ) {
			case 1:
				_localctx = new InsertOverwriteTableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1070);
				match(INSERT);
				setState(1071);
				match(OVERWRITE);
				setState(1072);
				match(TABLE);
				setState(1073);
				tableIdentifier();
				setState(1080);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(1074);
					partitionSpec();
					setState(1078);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==IF) {
						{
						setState(1075);
						match(IF);
						setState(1076);
						match(NOT);
						setState(1077);
						match(EXISTS);
						}
					}

					}
				}

				}
				break;
			case 2:
				_localctx = new InsertIntoTableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1082);
				match(INSERT);
				setState(1083);
				match(INTO);
				setState(1085);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
				case 1:
					{
					setState(1084);
					match(TABLE);
					}
					break;
				}
				setState(1087);
				tableIdentifier();
				setState(1089);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(1088);
					partitionSpec();
					}
				}

				}
				break;
			case 3:
				_localctx = new InsertOverwriteHiveDirContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1091);
				match(INSERT);
				setState(1092);
				match(OVERWRITE);
				setState(1094);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LOCAL) {
					{
					setState(1093);
					match(LOCAL);
					}
				}

				setState(1096);
				match(DIRECTORY);
				setState(1097);
				((InsertOverwriteHiveDirContext)_localctx).path = match(STRING);
				setState(1099);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ROW) {
					{
					setState(1098);
					rowFormat();
					}
				}

				setState(1102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STORED) {
					{
					setState(1101);
					createFileFormat();
					}
				}

				}
				break;
			case 4:
				_localctx = new InsertOverwriteDirContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1104);
				match(INSERT);
				setState(1105);
				match(OVERWRITE);
				setState(1107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LOCAL) {
					{
					setState(1106);
					match(LOCAL);
					}
				}

				setState(1109);
				match(DIRECTORY);
				setState(1111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(1110);
					((InsertOverwriteDirContext)_localctx).path = match(STRING);
					}
				}

				setState(1113);
				tableProvider();
				setState(1116);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OPTIONS) {
					{
					setState(1114);
					match(OPTIONS);
					setState(1115);
					((InsertOverwriteDirContext)_localctx).options = tablePropertyList();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PartitionSpecLocationContext extends ParserRuleContext {
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public LocationSpecContext locationSpec() {
			return getRuleContext(LocationSpecContext.class,0);
		}
		public PartitionSpecLocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionSpecLocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPartitionSpecLocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPartitionSpecLocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPartitionSpecLocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionSpecLocationContext partitionSpecLocation() throws RecognitionException {
		PartitionSpecLocationContext _localctx = new PartitionSpecLocationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_partitionSpecLocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1120);
			partitionSpec();
			setState(1122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LOCATION) {
				{
				setState(1121);
				locationSpec();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PartitionSpecContext extends ParserRuleContext {
		public TerminalNode PARTITION() { return getToken(NewSqlBaseParser.PARTITION, 0); }
		public List<PartitionValContext> partitionVal() {
			return getRuleContexts(PartitionValContext.class);
		}
		public PartitionValContext partitionVal(int i) {
			return getRuleContext(PartitionValContext.class,i);
		}
		public PartitionSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPartitionSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPartitionSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPartitionSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionSpecContext partitionSpec() throws RecognitionException {
		PartitionSpecContext _localctx = new PartitionSpecContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_partitionSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1124);
			match(PARTITION);
			setState(1125);
			match(T__0);
			setState(1126);
			partitionVal();
			setState(1131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1127);
				match(T__2);
				setState(1128);
				partitionVal();
				}
				}
				setState(1133);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1134);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PartitionValContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EQ() { return getToken(NewSqlBaseParser.EQ, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public PartitionValContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionVal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPartitionVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPartitionVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPartitionVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionValContext partitionVal() throws RecognitionException {
		PartitionValContext _localctx = new PartitionValContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_partitionVal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1136);
			identifier();
			setState(1139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQ) {
				{
				setState(1137);
				match(EQ);
				setState(1138);
				constant();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescribeFuncNameContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public ArithmeticOperatorContext arithmeticOperator() {
			return getRuleContext(ArithmeticOperatorContext.class,0);
		}
		public PredicateOperatorContext predicateOperator() {
			return getRuleContext(PredicateOperatorContext.class,0);
		}
		public DescribeFuncNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_describeFuncName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDescribeFuncName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDescribeFuncName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDescribeFuncName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescribeFuncNameContext describeFuncName() throws RecognitionException {
		DescribeFuncNameContext _localctx = new DescribeFuncNameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_describeFuncName);
		try {
			setState(1146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,125,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1141);
				qualifiedName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1142);
				match(STRING);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1143);
				comparisonOperator();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1144);
				arithmeticOperator();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1145);
				predicateOperator();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescribeColNameContext extends ParserRuleContext {
		public IdentifierContext identifier;
		public List<IdentifierContext> nameParts = new ArrayList<IdentifierContext>();
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public DescribeColNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_describeColName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDescribeColName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDescribeColName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDescribeColName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescribeColNameContext describeColName() throws RecognitionException {
		DescribeColNameContext _localctx = new DescribeColNameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_describeColName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1148);
			((DescribeColNameContext)_localctx).identifier = identifier();
			((DescribeColNameContext)_localctx).nameParts.add(((DescribeColNameContext)_localctx).identifier);
			setState(1153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(1149);
				match(T__3);
				setState(1150);
				((DescribeColNameContext)_localctx).identifier = identifier();
				((DescribeColNameContext)_localctx).nameParts.add(((DescribeColNameContext)_localctx).identifier);
				}
				}
				setState(1155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CtesContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public List<NamedQueryContext> namedQuery() {
			return getRuleContexts(NamedQueryContext.class);
		}
		public NamedQueryContext namedQuery(int i) {
			return getRuleContext(NamedQueryContext.class,i);
		}
		public CtesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ctes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCtes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCtes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCtes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CtesContext ctes() throws RecognitionException {
		CtesContext _localctx = new CtesContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_ctes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1156);
			match(WITH);
			setState(1157);
			namedQuery();
			setState(1162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1158);
				match(T__2);
				setState(1159);
				namedQuery();
				}
				}
				setState(1164);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedQueryContext extends ParserRuleContext {
		public IdentifierContext name;
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public NamedQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNamedQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNamedQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNamedQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedQueryContext namedQuery() throws RecognitionException {
		NamedQueryContext _localctx = new NamedQueryContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_namedQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1165);
			((NamedQueryContext)_localctx).name = identifier();
			setState(1167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(1166);
				match(AS);
				}
			}

			setState(1169);
			match(T__0);
			setState(1170);
			query();
			setState(1171);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableProviderContext extends ParserRuleContext {
		public TerminalNode USING() { return getToken(NewSqlBaseParser.USING, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TableProviderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableProvider; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableProvider(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableProvider(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableProvider(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableProviderContext tableProvider() throws RecognitionException {
		TableProviderContext _localctx = new TableProviderContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_tableProvider);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1173);
			match(USING);
			setState(1174);
			qualifiedName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TablePropertyListContext extends ParserRuleContext {
		public List<TablePropertyContext> tableProperty() {
			return getRuleContexts(TablePropertyContext.class);
		}
		public TablePropertyContext tableProperty(int i) {
			return getRuleContext(TablePropertyContext.class,i);
		}
		public TablePropertyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tablePropertyList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTablePropertyList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTablePropertyList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTablePropertyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TablePropertyListContext tablePropertyList() throws RecognitionException {
		TablePropertyListContext _localctx = new TablePropertyListContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_tablePropertyList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1176);
			match(T__0);
			setState(1177);
			tableProperty();
			setState(1182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1178);
				match(T__2);
				setState(1179);
				tableProperty();
				}
				}
				setState(1184);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1185);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TablePropertyContext extends ParserRuleContext {
		public TablePropertyKeyContext key;
		public TablePropertyValueContext value;
		public TablePropertyKeyContext tablePropertyKey() {
			return getRuleContext(TablePropertyKeyContext.class,0);
		}
		public TablePropertyValueContext tablePropertyValue() {
			return getRuleContext(TablePropertyValueContext.class,0);
		}
		public TerminalNode EQ() { return getToken(NewSqlBaseParser.EQ, 0); }
		public TablePropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableProperty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TablePropertyContext tableProperty() throws RecognitionException {
		TablePropertyContext _localctx = new TablePropertyContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_tableProperty);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1187);
			((TablePropertyContext)_localctx).key = tablePropertyKey();
			setState(1192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TRUE || _la==FALSE || _la==EQ || ((((_la - 248)) & ~0x3f) == 0 && ((1L << (_la - 248)) & ((1L << (STRING - 248)) | (1L << (INTEGER_VALUE - 248)) | (1L << (DECIMAL_VALUE - 248)))) != 0)) {
				{
				setState(1189);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EQ) {
					{
					setState(1188);
					match(EQ);
					}
				}

				setState(1191);
				((TablePropertyContext)_localctx).value = tablePropertyValue();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TablePropertyKeyContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TablePropertyKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tablePropertyKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTablePropertyKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTablePropertyKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTablePropertyKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TablePropertyKeyContext tablePropertyKey() throws RecognitionException {
		TablePropertyKeyContext _localctx = new TablePropertyKeyContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_tablePropertyKey);
		int _la;
		try {
			setState(1203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
			case FROM:
			case ADD:
			case AS:
			case ALL:
			case ANY:
			case DISTINCT:
			case WHERE:
			case GROUP:
			case BY:
			case GROUPING:
			case SETS:
			case CUBE:
			case ROLLUP:
			case ORDER:
			case HAVING:
			case LIMIT:
			case AT:
			case OR:
			case AND:
			case IN:
			case NOT:
			case NO:
			case EXISTS:
			case BETWEEN:
			case LIKE:
			case RLIKE:
			case IS:
			case NULL:
			case TRUE:
			case FALSE:
			case NULLS:
			case ASC:
			case DESC:
			case FOR:
			case INTERVAL:
			case CASE:
			case WHEN:
			case THEN:
			case ELSE:
			case END:
			case JOIN:
			case CROSS:
			case OUTER:
			case INNER:
			case LEFT:
			case SEMI:
			case RIGHT:
			case FULL:
			case NATURAL:
			case ON:
			case PIVOT:
			case LATERAL:
			case WINDOW:
			case OVER:
			case PARTITION:
			case RANGE:
			case ROWS:
			case UNBOUNDED:
			case PRECEDING:
			case FOLLOWING:
			case CURRENT:
			case FIRST:
			case AFTER:
			case LAST:
			case ROW:
			case WITH:
			case VALUES:
			case CREATE:
			case TABLE:
			case DIRECTORY:
			case VIEW:
			case REPLACE:
			case INSERT:
			case DELETE:
			case INTO:
			case DESCRIBE:
			case EXPLAIN:
			case FORMAT:
			case LOGICAL:
			case CODEGEN:
			case COST:
			case CAST:
			case SHOW:
			case TABLES:
			case COLUMNS:
			case COLUMN:
			case USE:
			case PARTITIONS:
			case FUNCTIONS:
			case DROP:
			case UNION:
			case EXCEPT:
			case SETMINUS:
			case INTERSECT:
			case TO:
			case TABLESAMPLE:
			case STRATIFY:
			case ALTER:
			case RENAME:
			case ARRAY:
			case MAP:
			case STRUCT:
			case COMMENT:
			case SET:
			case RESET:
			case DATA:
			case START:
			case TRANSACTION:
			case COMMIT:
			case ROLLBACK:
			case MACRO:
			case IGNORE:
			case BOTH:
			case LEADING:
			case TRAILING:
			case IF:
			case POSITION:
			case EXTRACT:
			case DIV:
			case PERCENTLIT:
			case BUCKET:
			case OUT:
			case OF:
			case SORT:
			case CLUSTER:
			case DISTRIBUTE:
			case OVERWRITE:
			case TRANSFORM:
			case REDUCE:
			case SERDE:
			case SERDEPROPERTIES:
			case RECORDREADER:
			case RECORDWRITER:
			case DELIMITED:
			case FIELDS:
			case TERMINATED:
			case COLLECTION:
			case ITEMS:
			case KEYS:
			case ESCAPED:
			case LINES:
			case SEPARATED:
			case FUNCTION:
			case EXTENDED:
			case REFRESH:
			case CLEAR:
			case CACHE:
			case UNCACHE:
			case LAZY:
			case FORMATTED:
			case GLOBAL:
			case TEMPORARY:
			case OPTIONS:
			case UNSET:
			case TBLPROPERTIES:
			case DBPROPERTIES:
			case BUCKETS:
			case SKEWED:
			case STORED:
			case DIRECTORIES:
			case LOCATION:
			case EXCHANGE:
			case ARCHIVE:
			case UNARCHIVE:
			case FILEFORMAT:
			case TOUCH:
			case COMPACT:
			case CONCATENATE:
			case CHANGE:
			case CASCADE:
			case RESTRICT:
			case CLUSTERED:
			case SORTED:
			case PURGE:
			case INPUTFORMAT:
			case OUTPUTFORMAT:
			case DATABASE:
			case DATABASES:
			case DFS:
			case TRUNCATE:
			case ANALYZE:
			case COMPUTE:
			case LIST:
			case STATISTICS:
			case PARTITIONED:
			case EXTERNAL:
			case DEFINED:
			case REVOKE:
			case GRANT:
			case LOCK:
			case UNLOCK:
			case MSCK:
			case REPAIR:
			case RECOVER:
			case EXPORT:
			case IMPORT:
			case LOAD:
			case ROLE:
			case ROLES:
			case COMPACTIONS:
			case PRINCIPALS:
			case TRANSACTIONS:
			case INDEX:
			case INDEXES:
			case LOCKS:
			case OPTION:
			case ANTI:
			case LOCAL:
			case INPATH:
			case IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1194);
				identifier();
				setState(1199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(1195);
					match(T__3);
					setState(1196);
					identifier();
					}
					}
					setState(1201);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(1202);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TablePropertyValueContext extends ParserRuleContext {
		public TerminalNode INTEGER_VALUE() { return getToken(NewSqlBaseParser.INTEGER_VALUE, 0); }
		public TerminalNode DECIMAL_VALUE() { return getToken(NewSqlBaseParser.DECIMAL_VALUE, 0); }
		public BooleanValueContext booleanValue() {
			return getRuleContext(BooleanValueContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TablePropertyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tablePropertyValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTablePropertyValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTablePropertyValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTablePropertyValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TablePropertyValueContext tablePropertyValue() throws RecognitionException {
		TablePropertyValueContext _localctx = new TablePropertyValueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_tablePropertyValue);
		try {
			setState(1209);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER_VALUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(1205);
				match(INTEGER_VALUE);
				}
				break;
			case DECIMAL_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1206);
				match(DECIMAL_VALUE);
				}
				break;
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(1207);
				booleanValue();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(1208);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantListContext extends ParserRuleContext {
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public ConstantListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterConstantList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitConstantList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitConstantList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantListContext constantList() throws RecognitionException {
		ConstantListContext _localctx = new ConstantListContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_constantList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1211);
			match(T__0);
			setState(1212);
			constant();
			setState(1217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1213);
				match(T__2);
				setState(1214);
				constant();
				}
				}
				setState(1219);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1220);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NestedConstantListContext extends ParserRuleContext {
		public List<ConstantListContext> constantList() {
			return getRuleContexts(ConstantListContext.class);
		}
		public ConstantListContext constantList(int i) {
			return getRuleContext(ConstantListContext.class,i);
		}
		public NestedConstantListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nestedConstantList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNestedConstantList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNestedConstantList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNestedConstantList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NestedConstantListContext nestedConstantList() throws RecognitionException {
		NestedConstantListContext _localctx = new NestedConstantListContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_nestedConstantList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1222);
			match(T__0);
			setState(1223);
			constantList();
			setState(1228);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1224);
				match(T__2);
				setState(1225);
				constantList();
				}
				}
				setState(1230);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1231);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateFileFormatContext extends ParserRuleContext {
		public TerminalNode STORED() { return getToken(NewSqlBaseParser.STORED, 0); }
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public FileFormatContext fileFormat() {
			return getRuleContext(FileFormatContext.class,0);
		}
		public TerminalNode BY() { return getToken(NewSqlBaseParser.BY, 0); }
		public StorageHandlerContext storageHandler() {
			return getRuleContext(StorageHandlerContext.class,0);
		}
		public CreateFileFormatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createFileFormat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCreateFileFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCreateFileFormat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCreateFileFormat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateFileFormatContext createFileFormat() throws RecognitionException {
		CreateFileFormatContext _localctx = new CreateFileFormatContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_createFileFormat);
		try {
			setState(1239);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,137,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1233);
				match(STORED);
				setState(1234);
				match(AS);
				setState(1235);
				fileFormat();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1236);
				match(STORED);
				setState(1237);
				match(BY);
				setState(1238);
				storageHandler();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileFormatContext extends ParserRuleContext {
		public FileFormatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileFormat; }
	 
		public FileFormatContext() { }
		public void copyFrom(FileFormatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TableFileFormatContext extends FileFormatContext {
		public Token inFmt;
		public Token outFmt;
		public TerminalNode INPUTFORMAT() { return getToken(NewSqlBaseParser.INPUTFORMAT, 0); }
		public TerminalNode OUTPUTFORMAT() { return getToken(NewSqlBaseParser.OUTPUTFORMAT, 0); }
		public List<TerminalNode> STRING() { return getTokens(NewSqlBaseParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(NewSqlBaseParser.STRING, i);
		}
		public TableFileFormatContext(FileFormatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableFileFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableFileFormat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableFileFormat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GenericFileFormatContext extends FileFormatContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public GenericFileFormatContext(FileFormatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterGenericFileFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitGenericFileFormat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitGenericFileFormat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileFormatContext fileFormat() throws RecognitionException {
		FileFormatContext _localctx = new FileFormatContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_fileFormat);
		try {
			setState(1246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,138,_ctx) ) {
			case 1:
				_localctx = new TableFileFormatContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1241);
				match(INPUTFORMAT);
				setState(1242);
				((TableFileFormatContext)_localctx).inFmt = match(STRING);
				setState(1243);
				match(OUTPUTFORMAT);
				setState(1244);
				((TableFileFormatContext)_localctx).outFmt = match(STRING);
				}
				break;
			case 2:
				_localctx = new GenericFileFormatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1245);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StorageHandlerContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public TerminalNode SERDEPROPERTIES() { return getToken(NewSqlBaseParser.SERDEPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public StorageHandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_storageHandler; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterStorageHandler(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitStorageHandler(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitStorageHandler(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StorageHandlerContext storageHandler() throws RecognitionException {
		StorageHandlerContext _localctx = new StorageHandlerContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_storageHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1248);
			match(STRING);
			setState(1252);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,139,_ctx) ) {
			case 1:
				{
				setState(1249);
				match(WITH);
				setState(1250);
				match(SERDEPROPERTIES);
				setState(1251);
				tablePropertyList();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResourceContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public ResourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resource; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterResource(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitResource(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitResource(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourceContext resource() throws RecognitionException {
		ResourceContext _localctx = new ResourceContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_resource);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1254);
			identifier();
			setState(1255);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryNoWithContext extends ParserRuleContext {
		public QueryNoWithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryNoWith; }
	 
		public QueryNoWithContext() { }
		public void copyFrom(QueryNoWithContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SingleInsertQueryContext extends QueryNoWithContext {
		public QueryTermContext queryTerm() {
			return getRuleContext(QueryTermContext.class,0);
		}
		public QueryOrganizationContext queryOrganization() {
			return getRuleContext(QueryOrganizationContext.class,0);
		}
		public InsertIntoContext insertInto() {
			return getRuleContext(InsertIntoContext.class,0);
		}
		public SingleInsertQueryContext(QueryNoWithContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSingleInsertQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSingleInsertQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSingleInsertQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiInsertQueryContext extends QueryNoWithContext {
		public FromClauseContext fromClause() {
			return getRuleContext(FromClauseContext.class,0);
		}
		public List<MultiInsertQueryBodyContext> multiInsertQueryBody() {
			return getRuleContexts(MultiInsertQueryBodyContext.class);
		}
		public MultiInsertQueryBodyContext multiInsertQueryBody(int i) {
			return getRuleContext(MultiInsertQueryBodyContext.class,i);
		}
		public MultiInsertQueryContext(QueryNoWithContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterMultiInsertQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitMultiInsertQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitMultiInsertQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryNoWithContext queryNoWith() throws RecognitionException {
		QueryNoWithContext _localctx = new QueryNoWithContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_queryNoWith);
		int _la;
		try {
			setState(1269);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,142,_ctx) ) {
			case 1:
				_localctx = new SingleInsertQueryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INSERT) {
					{
					setState(1257);
					insertInto();
					}
				}

				setState(1260);
				queryTerm(0);
				setState(1261);
				queryOrganization();
				}
				break;
			case 2:
				_localctx = new MultiInsertQueryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1263);
				fromClause();
				setState(1265); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1264);
					multiInsertQueryBody();
					}
					}
					setState(1267); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SELECT || _la==FROM || _la==INSERT || _la==MAP || _la==REDUCE );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryOrganizationContext extends ParserRuleContext {
		public SortItemContext sortItem;
		public List<SortItemContext> order = new ArrayList<SortItemContext>();
		public ExpressionContext expression;
		public List<ExpressionContext> clusterBy = new ArrayList<ExpressionContext>();
		public List<ExpressionContext> distributeBy = new ArrayList<ExpressionContext>();
		public List<SortItemContext> sort = new ArrayList<SortItemContext>();
		public ExpressionContext limit;
		public TerminalNode ORDER() { return getToken(NewSqlBaseParser.ORDER, 0); }
		public List<TerminalNode> BY() { return getTokens(NewSqlBaseParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(NewSqlBaseParser.BY, i);
		}
		public TerminalNode CLUSTER() { return getToken(NewSqlBaseParser.CLUSTER, 0); }
		public TerminalNode DISTRIBUTE() { return getToken(NewSqlBaseParser.DISTRIBUTE, 0); }
		public TerminalNode SORT() { return getToken(NewSqlBaseParser.SORT, 0); }
		public WindowsContext windows() {
			return getRuleContext(WindowsContext.class,0);
		}
		public TerminalNode LIMIT() { return getToken(NewSqlBaseParser.LIMIT, 0); }
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ALL() { return getToken(NewSqlBaseParser.ALL, 0); }
		public QueryOrganizationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryOrganization; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQueryOrganization(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQueryOrganization(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQueryOrganization(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryOrganizationContext queryOrganization() throws RecognitionException {
		QueryOrganizationContext _localctx = new QueryOrganizationContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_queryOrganization);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(1271);
				match(ORDER);
				setState(1272);
				match(BY);
				setState(1273);
				((QueryOrganizationContext)_localctx).sortItem = sortItem();
				((QueryOrganizationContext)_localctx).order.add(((QueryOrganizationContext)_localctx).sortItem);
				setState(1278);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1274);
					match(T__2);
					setState(1275);
					((QueryOrganizationContext)_localctx).sortItem = sortItem();
					((QueryOrganizationContext)_localctx).order.add(((QueryOrganizationContext)_localctx).sortItem);
					}
					}
					setState(1280);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1293);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CLUSTER) {
				{
				setState(1283);
				match(CLUSTER);
				setState(1284);
				match(BY);
				setState(1285);
				((QueryOrganizationContext)_localctx).expression = expression();
				((QueryOrganizationContext)_localctx).clusterBy.add(((QueryOrganizationContext)_localctx).expression);
				setState(1290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1286);
					match(T__2);
					setState(1287);
					((QueryOrganizationContext)_localctx).expression = expression();
					((QueryOrganizationContext)_localctx).clusterBy.add(((QueryOrganizationContext)_localctx).expression);
					}
					}
					setState(1292);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1305);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DISTRIBUTE) {
				{
				setState(1295);
				match(DISTRIBUTE);
				setState(1296);
				match(BY);
				setState(1297);
				((QueryOrganizationContext)_localctx).expression = expression();
				((QueryOrganizationContext)_localctx).distributeBy.add(((QueryOrganizationContext)_localctx).expression);
				setState(1302);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1298);
					match(T__2);
					setState(1299);
					((QueryOrganizationContext)_localctx).expression = expression();
					((QueryOrganizationContext)_localctx).distributeBy.add(((QueryOrganizationContext)_localctx).expression);
					}
					}
					setState(1304);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1317);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SORT) {
				{
				setState(1307);
				match(SORT);
				setState(1308);
				match(BY);
				setState(1309);
				((QueryOrganizationContext)_localctx).sortItem = sortItem();
				((QueryOrganizationContext)_localctx).sort.add(((QueryOrganizationContext)_localctx).sortItem);
				setState(1314);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1310);
					match(T__2);
					setState(1311);
					((QueryOrganizationContext)_localctx).sortItem = sortItem();
					((QueryOrganizationContext)_localctx).sort.add(((QueryOrganizationContext)_localctx).sortItem);
					}
					}
					setState(1316);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1320);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WINDOW) {
				{
				setState(1319);
				windows();
				}
			}

			setState(1327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(1322);
				match(LIMIT);
				setState(1325);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,152,_ctx) ) {
				case 1:
					{
					setState(1323);
					match(ALL);
					}
					break;
				case 2:
					{
					setState(1324);
					((QueryOrganizationContext)_localctx).limit = expression();
					}
					break;
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiInsertQueryBodyContext extends ParserRuleContext {
		public QuerySpecificationContext querySpecification() {
			return getRuleContext(QuerySpecificationContext.class,0);
		}
		public QueryOrganizationContext queryOrganization() {
			return getRuleContext(QueryOrganizationContext.class,0);
		}
		public InsertIntoContext insertInto() {
			return getRuleContext(InsertIntoContext.class,0);
		}
		public MultiInsertQueryBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiInsertQueryBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterMultiInsertQueryBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitMultiInsertQueryBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitMultiInsertQueryBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiInsertQueryBodyContext multiInsertQueryBody() throws RecognitionException {
		MultiInsertQueryBodyContext _localctx = new MultiInsertQueryBodyContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_multiInsertQueryBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1330);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INSERT) {
				{
				setState(1329);
				insertInto();
				}
			}

			setState(1332);
			querySpecification();
			setState(1333);
			queryOrganization();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryTermContext extends ParserRuleContext {
		public QueryTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryTerm; }
	 
		public QueryTermContext() { }
		public void copyFrom(QueryTermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class QueryTermDefaultContext extends QueryTermContext {
		public QueryPrimaryContext queryPrimary() {
			return getRuleContext(QueryPrimaryContext.class,0);
		}
		public QueryTermDefaultContext(QueryTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQueryTermDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQueryTermDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQueryTermDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetOperationContext extends QueryTermContext {
		public QueryTermContext left;
		public Token operator;
		public QueryTermContext right;
		public List<QueryTermContext> queryTerm() {
			return getRuleContexts(QueryTermContext.class);
		}
		public QueryTermContext queryTerm(int i) {
			return getRuleContext(QueryTermContext.class,i);
		}
		public TerminalNode INTERSECT() { return getToken(NewSqlBaseParser.INTERSECT, 0); }
		public TerminalNode UNION() { return getToken(NewSqlBaseParser.UNION, 0); }
		public TerminalNode EXCEPT() { return getToken(NewSqlBaseParser.EXCEPT, 0); }
		public TerminalNode SETMINUS() { return getToken(NewSqlBaseParser.SETMINUS, 0); }
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public SetOperationContext(QueryTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryTermContext queryTerm() throws RecognitionException {
		return queryTerm(0);
	}

	private QueryTermContext queryTerm(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		QueryTermContext _localctx = new QueryTermContext(_ctx, _parentState);
		QueryTermContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_queryTerm, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new QueryTermDefaultContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1336);
			queryPrimary();
			}
			_ctx.stop = _input.LT(-1);
			setState(1361);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,159,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1359);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,158,_ctx) ) {
					case 1:
						{
						_localctx = new SetOperationContext(new QueryTermContext(_parentctx, _parentState));
						((SetOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_queryTerm);
						setState(1338);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(1339);
						if (!(legacy_setops_precedence_enbled)) throw new FailedPredicateException(this, "legacy_setops_precedence_enbled");
						setState(1340);
						((SetOperationContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 110)) & ~0x3f) == 0 && ((1L << (_la - 110)) & ((1L << (UNION - 110)) | (1L << (EXCEPT - 110)) | (1L << (SETMINUS - 110)) | (1L << (INTERSECT - 110)))) != 0)) ) {
							((SetOperationContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1342);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==ALL || _la==DISTINCT) {
							{
							setState(1341);
							setQuantifier();
							}
						}

						setState(1344);
						((SetOperationContext)_localctx).right = queryTerm(4);
						}
						break;
					case 2:
						{
						_localctx = new SetOperationContext(new QueryTermContext(_parentctx, _parentState));
						((SetOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_queryTerm);
						setState(1345);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1346);
						if (!(!legacy_setops_precedence_enbled)) throw new FailedPredicateException(this, "!legacy_setops_precedence_enbled");
						setState(1347);
						((SetOperationContext)_localctx).operator = match(INTERSECT);
						setState(1349);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==ALL || _la==DISTINCT) {
							{
							setState(1348);
							setQuantifier();
							}
						}

						setState(1351);
						((SetOperationContext)_localctx).right = queryTerm(3);
						}
						break;
					case 3:
						{
						_localctx = new SetOperationContext(new QueryTermContext(_parentctx, _parentState));
						((SetOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_queryTerm);
						setState(1352);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1353);
						if (!(!legacy_setops_precedence_enbled)) throw new FailedPredicateException(this, "!legacy_setops_precedence_enbled");
						setState(1354);
						((SetOperationContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 110)) & ~0x3f) == 0 && ((1L << (_la - 110)) & ((1L << (UNION - 110)) | (1L << (EXCEPT - 110)) | (1L << (SETMINUS - 110)))) != 0)) ) {
							((SetOperationContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1356);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==ALL || _la==DISTINCT) {
							{
							setState(1355);
							setQuantifier();
							}
						}

						setState(1358);
						((SetOperationContext)_localctx).right = queryTerm(2);
						}
						break;
					}
					} 
				}
				setState(1363);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,159,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class QueryPrimaryContext extends ParserRuleContext {
		public QueryPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryPrimary; }
	 
		public QueryPrimaryContext() { }
		public void copyFrom(QueryPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SubqueryContext extends QueryPrimaryContext {
		public QueryNoWithContext queryNoWith() {
			return getRuleContext(QueryNoWithContext.class,0);
		}
		public SubqueryContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSubquery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSubquery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSubquery(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class QueryPrimaryDefaultContext extends QueryPrimaryContext {
		public QuerySpecificationContext querySpecification() {
			return getRuleContext(QuerySpecificationContext.class,0);
		}
		public QueryPrimaryDefaultContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQueryPrimaryDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQueryPrimaryDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQueryPrimaryDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InlineTableDefault1Context extends QueryPrimaryContext {
		public InlineTableContext inlineTable() {
			return getRuleContext(InlineTableContext.class,0);
		}
		public InlineTableDefault1Context(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInlineTableDefault1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInlineTableDefault1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInlineTableDefault1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TableContext extends QueryPrimaryContext {
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TableContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryPrimaryContext queryPrimary() throws RecognitionException {
		QueryPrimaryContext _localctx = new QueryPrimaryContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_queryPrimary);
		try {
			setState(1372);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
			case FROM:
			case MAP:
			case REDUCE:
				_localctx = new QueryPrimaryDefaultContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1364);
				querySpecification();
				}
				break;
			case TABLE:
				_localctx = new TableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1365);
				match(TABLE);
				setState(1366);
				tableIdentifier();
				}
				break;
			case VALUES:
				_localctx = new InlineTableDefault1Context(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1367);
				inlineTable();
				}
				break;
			case T__0:
				_localctx = new SubqueryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1368);
				match(T__0);
				setState(1369);
				queryNoWith();
				setState(1370);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SortItemContext extends ParserRuleContext {
		public Token ordering;
		public Token nullOrder;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NULLS() { return getToken(NewSqlBaseParser.NULLS, 0); }
		public TerminalNode ASC() { return getToken(NewSqlBaseParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(NewSqlBaseParser.DESC, 0); }
		public TerminalNode LAST() { return getToken(NewSqlBaseParser.LAST, 0); }
		public TerminalNode FIRST() { return getToken(NewSqlBaseParser.FIRST, 0); }
		public SortItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sortItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSortItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSortItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSortItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SortItemContext sortItem() throws RecognitionException {
		SortItemContext _localctx = new SortItemContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_sortItem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1374);
			expression();
			setState(1376);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(1375);
				((SortItemContext)_localctx).ordering = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
					((SortItemContext)_localctx).ordering = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1380);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NULLS) {
				{
				setState(1378);
				match(NULLS);
				setState(1379);
				((SortItemContext)_localctx).nullOrder = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==FIRST || _la==LAST) ) {
					((SortItemContext)_localctx).nullOrder = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuerySpecificationContext extends ParserRuleContext {
		public Token kind;
		public RowFormatContext inRowFormat;
		public Token recordWriter;
		public Token script;
		public RowFormatContext outRowFormat;
		public Token recordReader;
		public BooleanExpressionContext where;
		public HintContext hint;
		public List<HintContext> hints = new ArrayList<HintContext>();
		public BooleanExpressionContext having;
		public TerminalNode USING() { return getToken(NewSqlBaseParser.USING, 0); }
		public List<TerminalNode> STRING() { return getTokens(NewSqlBaseParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(NewSqlBaseParser.STRING, i);
		}
		public TerminalNode RECORDWRITER() { return getToken(NewSqlBaseParser.RECORDWRITER, 0); }
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public TerminalNode RECORDREADER() { return getToken(NewSqlBaseParser.RECORDREADER, 0); }
		public FromClauseContext fromClause() {
			return getRuleContext(FromClauseContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(NewSqlBaseParser.WHERE, 0); }
		public TerminalNode SELECT() { return getToken(NewSqlBaseParser.SELECT, 0); }
		public NamedExpressionSeqContext namedExpressionSeq() {
			return getRuleContext(NamedExpressionSeqContext.class,0);
		}
		public List<RowFormatContext> rowFormat() {
			return getRuleContexts(RowFormatContext.class);
		}
		public RowFormatContext rowFormat(int i) {
			return getRuleContext(RowFormatContext.class,i);
		}
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public TerminalNode TRANSFORM() { return getToken(NewSqlBaseParser.TRANSFORM, 0); }
		public TerminalNode MAP() { return getToken(NewSqlBaseParser.MAP, 0); }
		public TerminalNode REDUCE() { return getToken(NewSqlBaseParser.REDUCE, 0); }
		public IdentifierSeqContext identifierSeq() {
			return getRuleContext(IdentifierSeqContext.class,0);
		}
		public ColTypeListContext colTypeList() {
			return getRuleContext(ColTypeListContext.class,0);
		}
		public List<LateralViewContext> lateralView() {
			return getRuleContexts(LateralViewContext.class);
		}
		public LateralViewContext lateralView(int i) {
			return getRuleContext(LateralViewContext.class,i);
		}
		public AggregationContext aggregation() {
			return getRuleContext(AggregationContext.class,0);
		}
		public TerminalNode HAVING() { return getToken(NewSqlBaseParser.HAVING, 0); }
		public WindowsContext windows() {
			return getRuleContext(WindowsContext.class,0);
		}
		public AqpContext aqp() {
			return getRuleContext(AqpContext.class,0);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public List<HintContext> hint() {
			return getRuleContexts(HintContext.class);
		}
		public HintContext hint(int i) {
			return getRuleContext(HintContext.class,i);
		}
		public QuerySpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_querySpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQuerySpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQuerySpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQuerySpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuerySpecificationContext querySpecification() throws RecognitionException {
		QuerySpecificationContext _localctx = new QuerySpecificationContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_querySpecification);
		int _la;
		try {
			int _alt;
			setState(1478);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,185,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				{
				setState(1392);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SELECT:
					{
					setState(1382);
					match(SELECT);
					setState(1383);
					((QuerySpecificationContext)_localctx).kind = match(TRANSFORM);
					setState(1384);
					match(T__0);
					setState(1385);
					namedExpressionSeq();
					setState(1386);
					match(T__1);
					}
					break;
				case MAP:
					{
					setState(1388);
					((QuerySpecificationContext)_localctx).kind = match(MAP);
					setState(1389);
					namedExpressionSeq();
					}
					break;
				case REDUCE:
					{
					setState(1390);
					((QuerySpecificationContext)_localctx).kind = match(REDUCE);
					setState(1391);
					namedExpressionSeq();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(1395);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ROW) {
					{
					setState(1394);
					((QuerySpecificationContext)_localctx).inRowFormat = rowFormat();
					}
				}

				setState(1399);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RECORDWRITER) {
					{
					setState(1397);
					match(RECORDWRITER);
					setState(1398);
					((QuerySpecificationContext)_localctx).recordWriter = match(STRING);
					}
				}

				setState(1401);
				match(USING);
				setState(1402);
				((QuerySpecificationContext)_localctx).script = match(STRING);
				setState(1415);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,168,_ctx) ) {
				case 1:
					{
					setState(1403);
					match(AS);
					setState(1413);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,167,_ctx) ) {
					case 1:
						{
						setState(1404);
						identifierSeq();
						}
						break;
					case 2:
						{
						setState(1405);
						colTypeList();
						}
						break;
					case 3:
						{
						{
						setState(1406);
						match(T__0);
						setState(1409);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,166,_ctx) ) {
						case 1:
							{
							setState(1407);
							identifierSeq();
							}
							break;
						case 2:
							{
							setState(1408);
							colTypeList();
							}
							break;
						}
						setState(1411);
						match(T__1);
						}
						}
						break;
					}
					}
					break;
				}
				setState(1418);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,169,_ctx) ) {
				case 1:
					{
					setState(1417);
					((QuerySpecificationContext)_localctx).outRowFormat = rowFormat();
					}
					break;
				}
				setState(1422);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,170,_ctx) ) {
				case 1:
					{
					setState(1420);
					match(RECORDREADER);
					setState(1421);
					((QuerySpecificationContext)_localctx).recordReader = match(STRING);
					}
					break;
				}
				setState(1425);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
				case 1:
					{
					setState(1424);
					fromClause();
					}
					break;
				}
				setState(1429);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,172,_ctx) ) {
				case 1:
					{
					setState(1427);
					match(WHERE);
					setState(1428);
					((QuerySpecificationContext)_localctx).where = booleanExpression(0);
					}
					break;
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(1453);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SELECT:
					{
					setState(1431);
					((QuerySpecificationContext)_localctx).kind = match(SELECT);
					setState(1435);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__4) {
						{
						{
						setState(1432);
						((QuerySpecificationContext)_localctx).hint = hint();
						((QuerySpecificationContext)_localctx).hints.add(((QuerySpecificationContext)_localctx).hint);
						}
						}
						setState(1437);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1439);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,174,_ctx) ) {
					case 1:
						{
						setState(1438);
						setQuantifier();
						}
						break;
					}
					setState(1441);
					namedExpressionSeq();
					setState(1443);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,175,_ctx) ) {
					case 1:
						{
						setState(1442);
						fromClause();
						}
						break;
					}
					}
					break;
				case FROM:
					{
					setState(1445);
					fromClause();
					setState(1451);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
					case 1:
						{
						setState(1446);
						((QuerySpecificationContext)_localctx).kind = match(SELECT);
						setState(1448);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,176,_ctx) ) {
						case 1:
							{
							setState(1447);
							setQuantifier();
							}
							break;
						}
						setState(1450);
						namedExpressionSeq();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1458);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,179,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1455);
						lateralView();
						}
						} 
					}
					setState(1460);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,179,_ctx);
				}
				setState(1463);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,180,_ctx) ) {
				case 1:
					{
					setState(1461);
					match(WHERE);
					setState(1462);
					((QuerySpecificationContext)_localctx).where = booleanExpression(0);
					}
					break;
				}
				setState(1466);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,181,_ctx) ) {
				case 1:
					{
					setState(1465);
					aggregation();
					}
					break;
				}
				setState(1470);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,182,_ctx) ) {
				case 1:
					{
					setState(1468);
					match(HAVING);
					setState(1469);
					((QuerySpecificationContext)_localctx).having = booleanExpression(0);
					}
					break;
				}
				setState(1473);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,183,_ctx) ) {
				case 1:
					{
					setState(1472);
					windows();
					}
					break;
				}
				setState(1476);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,184,_ctx) ) {
				case 1:
					{
					setState(1475);
					aqp();
					}
					break;
				}
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AqpContext extends ParserRuleContext {
		public ErrorContext error() {
			return getRuleContext(ErrorContext.class,0);
		}
		public ConfidenceContext confidence() {
			return getRuleContext(ConfidenceContext.class,0);
		}
		public AqpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aqp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAqp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAqp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAqp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AqpContext aqp() throws RecognitionException {
		AqpContext _localctx = new AqpContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_aqp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1480);
			error();
			setState(1481);
			confidence();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ErrorContext extends ParserRuleContext {
		public TerminalNode ERROR() { return getToken(NewSqlBaseParser.ERROR, 0); }
		public TerminalNode WITHIN() { return getToken(NewSqlBaseParser.WITHIN, 0); }
		public TerminalNode PERCENTAGE() { return getToken(NewSqlBaseParser.PERCENTAGE, 0); }
		public ErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterError(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitError(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitError(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ErrorContext error() throws RecognitionException {
		ErrorContext _localctx = new ErrorContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_error);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1483);
			match(ERROR);
			setState(1484);
			match(WITHIN);
			setState(1485);
			match(PERCENTAGE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConfidenceContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(NewSqlBaseParser.AT, 0); }
		public TerminalNode CONFIDENCE() { return getToken(NewSqlBaseParser.CONFIDENCE, 0); }
		public TerminalNode PERCENTAGE() { return getToken(NewSqlBaseParser.PERCENTAGE, 0); }
		public ConfidenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_confidence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterConfidence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitConfidence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitConfidence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfidenceContext confidence() throws RecognitionException {
		ConfidenceContext _localctx = new ConfidenceContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_confidence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1487);
			match(AT);
			setState(1488);
			match(CONFIDENCE);
			setState(1489);
			match(PERCENTAGE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HintContext extends ParserRuleContext {
		public HintStatementContext hintStatement;
		public List<HintStatementContext> hintStatements = new ArrayList<HintStatementContext>();
		public List<HintStatementContext> hintStatement() {
			return getRuleContexts(HintStatementContext.class);
		}
		public HintStatementContext hintStatement(int i) {
			return getRuleContext(HintStatementContext.class,i);
		}
		public HintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterHint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitHint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitHint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HintContext hint() throws RecognitionException {
		HintContext _localctx = new HintContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_hint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1491);
			match(T__4);
			setState(1492);
			((HintContext)_localctx).hintStatement = hintStatement();
			((HintContext)_localctx).hintStatements.add(((HintContext)_localctx).hintStatement);
			setState(1499);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (T__2 - 3)) | (1L << (SELECT - 3)) | (1L << (FROM - 3)) | (1L << (ADD - 3)) | (1L << (AS - 3)) | (1L << (ALL - 3)) | (1L << (ANY - 3)) | (1L << (DISTINCT - 3)) | (1L << (WHERE - 3)) | (1L << (GROUP - 3)) | (1L << (BY - 3)) | (1L << (GROUPING - 3)) | (1L << (SETS - 3)) | (1L << (CUBE - 3)) | (1L << (ROLLUP - 3)) | (1L << (ORDER - 3)) | (1L << (HAVING - 3)) | (1L << (LIMIT - 3)) | (1L << (AT - 3)) | (1L << (OR - 3)) | (1L << (AND - 3)) | (1L << (IN - 3)) | (1L << (NOT - 3)) | (1L << (NO - 3)) | (1L << (EXISTS - 3)) | (1L << (BETWEEN - 3)) | (1L << (LIKE - 3)) | (1L << (RLIKE - 3)) | (1L << (IS - 3)) | (1L << (NULL - 3)) | (1L << (TRUE - 3)) | (1L << (FALSE - 3)) | (1L << (NULLS - 3)) | (1L << (ASC - 3)) | (1L << (DESC - 3)) | (1L << (FOR - 3)) | (1L << (INTERVAL - 3)) | (1L << (CASE - 3)) | (1L << (WHEN - 3)) | (1L << (THEN - 3)) | (1L << (ELSE - 3)) | (1L << (END - 3)) | (1L << (JOIN - 3)) | (1L << (CROSS - 3)) | (1L << (OUTER - 3)) | (1L << (INNER - 3)) | (1L << (LEFT - 3)) | (1L << (SEMI - 3)) | (1L << (RIGHT - 3)) | (1L << (FULL - 3)) | (1L << (NATURAL - 3)) | (1L << (ON - 3)) | (1L << (PIVOT - 3)) | (1L << (LATERAL - 3)) | (1L << (WINDOW - 3)) | (1L << (OVER - 3)) | (1L << (PARTITION - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (RANGE - 67)) | (1L << (ROWS - 67)) | (1L << (UNBOUNDED - 67)) | (1L << (PRECEDING - 67)) | (1L << (FOLLOWING - 67)) | (1L << (CURRENT - 67)) | (1L << (FIRST - 67)) | (1L << (AFTER - 67)) | (1L << (LAST - 67)) | (1L << (ROW - 67)) | (1L << (WITH - 67)) | (1L << (VALUES - 67)) | (1L << (CREATE - 67)) | (1L << (TABLE - 67)) | (1L << (DIRECTORY - 67)) | (1L << (VIEW - 67)) | (1L << (REPLACE - 67)) | (1L << (INSERT - 67)) | (1L << (DELETE - 67)) | (1L << (INTO - 67)) | (1L << (DESCRIBE - 67)) | (1L << (EXPLAIN - 67)) | (1L << (FORMAT - 67)) | (1L << (LOGICAL - 67)) | (1L << (CODEGEN - 67)) | (1L << (COST - 67)) | (1L << (CAST - 67)) | (1L << (SHOW - 67)) | (1L << (TABLES - 67)) | (1L << (COLUMNS - 67)) | (1L << (COLUMN - 67)) | (1L << (USE - 67)) | (1L << (PARTITIONS - 67)) | (1L << (FUNCTIONS - 67)) | (1L << (DROP - 67)) | (1L << (UNION - 67)) | (1L << (EXCEPT - 67)) | (1L << (SETMINUS - 67)) | (1L << (INTERSECT - 67)) | (1L << (TO - 67)) | (1L << (TABLESAMPLE - 67)) | (1L << (STRATIFY - 67)) | (1L << (ALTER - 67)) | (1L << (RENAME - 67)) | (1L << (ARRAY - 67)) | (1L << (MAP - 67)) | (1L << (STRUCT - 67)) | (1L << (COMMENT - 67)) | (1L << (SET - 67)) | (1L << (RESET - 67)) | (1L << (DATA - 67)) | (1L << (START - 67)) | (1L << (TRANSACTION - 67)) | (1L << (COMMIT - 67)) | (1L << (ROLLBACK - 67)) | (1L << (MACRO - 67)))) != 0) || ((((_la - 131)) & ~0x3f) == 0 && ((1L << (_la - 131)) & ((1L << (IGNORE - 131)) | (1L << (BOTH - 131)) | (1L << (LEADING - 131)) | (1L << (TRAILING - 131)) | (1L << (IF - 131)) | (1L << (POSITION - 131)) | (1L << (EXTRACT - 131)) | (1L << (DIV - 131)) | (1L << (PERCENTLIT - 131)) | (1L << (BUCKET - 131)) | (1L << (OUT - 131)) | (1L << (OF - 131)) | (1L << (SORT - 131)) | (1L << (CLUSTER - 131)) | (1L << (DISTRIBUTE - 131)) | (1L << (OVERWRITE - 131)) | (1L << (TRANSFORM - 131)) | (1L << (REDUCE - 131)) | (1L << (SERDE - 131)) | (1L << (SERDEPROPERTIES - 131)) | (1L << (RECORDREADER - 131)) | (1L << (RECORDWRITER - 131)) | (1L << (DELIMITED - 131)) | (1L << (FIELDS - 131)) | (1L << (TERMINATED - 131)) | (1L << (COLLECTION - 131)) | (1L << (ITEMS - 131)) | (1L << (KEYS - 131)) | (1L << (ESCAPED - 131)) | (1L << (LINES - 131)) | (1L << (SEPARATED - 131)) | (1L << (FUNCTION - 131)) | (1L << (EXTENDED - 131)) | (1L << (REFRESH - 131)) | (1L << (CLEAR - 131)) | (1L << (CACHE - 131)) | (1L << (UNCACHE - 131)) | (1L << (LAZY - 131)) | (1L << (FORMATTED - 131)) | (1L << (GLOBAL - 131)) | (1L << (TEMPORARY - 131)) | (1L << (OPTIONS - 131)) | (1L << (UNSET - 131)) | (1L << (TBLPROPERTIES - 131)) | (1L << (DBPROPERTIES - 131)))) != 0) || ((((_la - 195)) & ~0x3f) == 0 && ((1L << (_la - 195)) & ((1L << (BUCKETS - 195)) | (1L << (SKEWED - 195)) | (1L << (STORED - 195)) | (1L << (DIRECTORIES - 195)) | (1L << (LOCATION - 195)) | (1L << (EXCHANGE - 195)) | (1L << (ARCHIVE - 195)) | (1L << (UNARCHIVE - 195)) | (1L << (FILEFORMAT - 195)) | (1L << (TOUCH - 195)) | (1L << (COMPACT - 195)) | (1L << (CONCATENATE - 195)) | (1L << (CHANGE - 195)) | (1L << (CASCADE - 195)) | (1L << (RESTRICT - 195)) | (1L << (CLUSTERED - 195)) | (1L << (SORTED - 195)) | (1L << (PURGE - 195)) | (1L << (INPUTFORMAT - 195)) | (1L << (OUTPUTFORMAT - 195)) | (1L << (DATABASE - 195)) | (1L << (DATABASES - 195)) | (1L << (DFS - 195)) | (1L << (TRUNCATE - 195)) | (1L << (ANALYZE - 195)) | (1L << (COMPUTE - 195)) | (1L << (LIST - 195)) | (1L << (STATISTICS - 195)) | (1L << (PARTITIONED - 195)) | (1L << (EXTERNAL - 195)) | (1L << (DEFINED - 195)) | (1L << (REVOKE - 195)) | (1L << (GRANT - 195)) | (1L << (LOCK - 195)) | (1L << (UNLOCK - 195)) | (1L << (MSCK - 195)) | (1L << (REPAIR - 195)) | (1L << (RECOVER - 195)) | (1L << (EXPORT - 195)) | (1L << (IMPORT - 195)) | (1L << (LOAD - 195)) | (1L << (ROLE - 195)) | (1L << (ROLES - 195)) | (1L << (COMPACTIONS - 195)) | (1L << (PRINCIPALS - 195)) | (1L << (TRANSACTIONS - 195)) | (1L << (INDEX - 195)) | (1L << (INDEXES - 195)) | (1L << (LOCKS - 195)) | (1L << (OPTION - 195)) | (1L << (ANTI - 195)) | (1L << (LOCAL - 195)) | (1L << (INPATH - 195)) | (1L << (IDENTIFIER - 195)) | (1L << (BACKQUOTED_IDENTIFIER - 195)))) != 0)) {
				{
				{
				setState(1494);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1493);
					match(T__2);
					}
				}

				setState(1496);
				((HintContext)_localctx).hintStatement = hintStatement();
				((HintContext)_localctx).hintStatements.add(((HintContext)_localctx).hintStatement);
				}
				}
				setState(1501);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1502);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HintStatementContext extends ParserRuleContext {
		public IdentifierContext hintName;
		public PrimaryExpressionContext primaryExpression;
		public List<PrimaryExpressionContext> parameters = new ArrayList<PrimaryExpressionContext>();
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<PrimaryExpressionContext> primaryExpression() {
			return getRuleContexts(PrimaryExpressionContext.class);
		}
		public PrimaryExpressionContext primaryExpression(int i) {
			return getRuleContext(PrimaryExpressionContext.class,i);
		}
		public HintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hintStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterHintStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitHintStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitHintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HintStatementContext hintStatement() throws RecognitionException {
		HintStatementContext _localctx = new HintStatementContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_hintStatement);
		int _la;
		try {
			setState(1517);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,189,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1504);
				((HintStatementContext)_localctx).hintName = identifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1505);
				((HintStatementContext)_localctx).hintName = identifier();
				setState(1506);
				match(T__0);
				setState(1507);
				((HintStatementContext)_localctx).primaryExpression = primaryExpression(0);
				((HintStatementContext)_localctx).parameters.add(((HintStatementContext)_localctx).primaryExpression);
				setState(1512);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1508);
					match(T__2);
					setState(1509);
					((HintStatementContext)_localctx).primaryExpression = primaryExpression(0);
					((HintStatementContext)_localctx).parameters.add(((HintStatementContext)_localctx).primaryExpression);
					}
					}
					setState(1514);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1515);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FromClauseContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public List<RelationContext> relation() {
			return getRuleContexts(RelationContext.class);
		}
		public RelationContext relation(int i) {
			return getRuleContext(RelationContext.class,i);
		}
		public List<LateralViewContext> lateralView() {
			return getRuleContexts(LateralViewContext.class);
		}
		public LateralViewContext lateralView(int i) {
			return getRuleContext(LateralViewContext.class,i);
		}
		public PivotClauseContext pivotClause() {
			return getRuleContext(PivotClauseContext.class,0);
		}
		public FromClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fromClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFromClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFromClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFromClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FromClauseContext fromClause() throws RecognitionException {
		FromClauseContext _localctx = new FromClauseContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_fromClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1519);
			match(FROM);
			setState(1520);
			relation();
			setState(1525);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,190,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1521);
					match(T__2);
					setState(1522);
					relation();
					}
					} 
				}
				setState(1527);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,190,_ctx);
			}
			setState(1531);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,191,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1528);
					lateralView();
					}
					} 
				}
				setState(1533);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,191,_ctx);
			}
			setState(1535);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,192,_ctx) ) {
			case 1:
				{
				setState(1534);
				pivotClause();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AggregationContext extends ParserRuleContext {
		public ExpressionContext expression;
		public List<ExpressionContext> groupingExpressions = new ArrayList<ExpressionContext>();
		public Token kind;
		public TerminalNode GROUP() { return getToken(NewSqlBaseParser.GROUP, 0); }
		public TerminalNode BY() { return getToken(NewSqlBaseParser.BY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public TerminalNode SETS() { return getToken(NewSqlBaseParser.SETS, 0); }
		public List<GroupingSetContext> groupingSet() {
			return getRuleContexts(GroupingSetContext.class);
		}
		public GroupingSetContext groupingSet(int i) {
			return getRuleContext(GroupingSetContext.class,i);
		}
		public TerminalNode ROLLUP() { return getToken(NewSqlBaseParser.ROLLUP, 0); }
		public TerminalNode CUBE() { return getToken(NewSqlBaseParser.CUBE, 0); }
		public TerminalNode GROUPING() { return getToken(NewSqlBaseParser.GROUPING, 0); }
		public AggregationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAggregation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAggregation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAggregation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AggregationContext aggregation() throws RecognitionException {
		AggregationContext _localctx = new AggregationContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_aggregation);
		int _la;
		try {
			int _alt;
			setState(1581);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,197,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1537);
				match(GROUP);
				setState(1538);
				match(BY);
				setState(1539);
				((AggregationContext)_localctx).expression = expression();
				((AggregationContext)_localctx).groupingExpressions.add(((AggregationContext)_localctx).expression);
				setState(1544);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,193,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1540);
						match(T__2);
						setState(1541);
						((AggregationContext)_localctx).expression = expression();
						((AggregationContext)_localctx).groupingExpressions.add(((AggregationContext)_localctx).expression);
						}
						} 
					}
					setState(1546);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,193,_ctx);
				}
				setState(1564);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,195,_ctx) ) {
				case 1:
					{
					setState(1547);
					match(WITH);
					setState(1548);
					((AggregationContext)_localctx).kind = match(ROLLUP);
					}
					break;
				case 2:
					{
					setState(1549);
					match(WITH);
					setState(1550);
					((AggregationContext)_localctx).kind = match(CUBE);
					}
					break;
				case 3:
					{
					setState(1551);
					((AggregationContext)_localctx).kind = match(GROUPING);
					setState(1552);
					match(SETS);
					setState(1553);
					match(T__0);
					setState(1554);
					groupingSet();
					setState(1559);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(1555);
						match(T__2);
						setState(1556);
						groupingSet();
						}
						}
						setState(1561);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1562);
					match(T__1);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1566);
				match(GROUP);
				setState(1567);
				match(BY);
				setState(1568);
				((AggregationContext)_localctx).kind = match(GROUPING);
				setState(1569);
				match(SETS);
				setState(1570);
				match(T__0);
				setState(1571);
				groupingSet();
				setState(1576);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1572);
					match(T__2);
					setState(1573);
					groupingSet();
					}
					}
					setState(1578);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1579);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupingSetContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public GroupingSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupingSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterGroupingSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitGroupingSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitGroupingSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupingSetContext groupingSet() throws RecognitionException {
		GroupingSetContext _localctx = new GroupingSetContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_groupingSet);
		int _la;
		try {
			setState(1596);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,200,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1583);
				match(T__0);
				setState(1592);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << JOIN) | (1L << CROSS) | (1L << OUTER) | (1L << INNER) | (1L << LEFT) | (1L << SEMI) | (1L << RIGHT) | (1L << FULL) | (1L << NATURAL) | (1L << ON) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (UNION - 64)) | (1L << (EXCEPT - 64)) | (1L << (SETMINUS - 64)) | (1L << (INTERSECT - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (PLUS - 128)) | (1L << (MINUS - 128)) | (1L << (ASTERISK - 128)) | (1L << (DIV - 128)) | (1L << (TILDE - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (ANTI - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)) | (1L << (STRING - 192)) | (1L << (BIGINT_LITERAL - 192)) | (1L << (SMALLINT_LITERAL - 192)) | (1L << (TINYINT_LITERAL - 192)) | (1L << (INTEGER_VALUE - 192)) | (1L << (DECIMAL_VALUE - 192)) | (1L << (DOUBLE_LITERAL - 192)) | (1L << (BIGDECIMAL_LITERAL - 192)))) != 0) || _la==IDENTIFIER || _la==BACKQUOTED_IDENTIFIER) {
					{
					setState(1584);
					expression();
					setState(1589);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(1585);
						match(T__2);
						setState(1586);
						expression();
						}
						}
						setState(1591);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1594);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1595);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PivotClauseContext extends ParserRuleContext {
		public NamedExpressionSeqContext aggregates;
		public PivotValueContext pivotValue;
		public List<PivotValueContext> pivotValues = new ArrayList<PivotValueContext>();
		public TerminalNode PIVOT() { return getToken(NewSqlBaseParser.PIVOT, 0); }
		public TerminalNode FOR() { return getToken(NewSqlBaseParser.FOR, 0); }
		public PivotColumnContext pivotColumn() {
			return getRuleContext(PivotColumnContext.class,0);
		}
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public NamedExpressionSeqContext namedExpressionSeq() {
			return getRuleContext(NamedExpressionSeqContext.class,0);
		}
		public List<PivotValueContext> pivotValue() {
			return getRuleContexts(PivotValueContext.class);
		}
		public PivotValueContext pivotValue(int i) {
			return getRuleContext(PivotValueContext.class,i);
		}
		public PivotClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pivotClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPivotClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPivotClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPivotClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PivotClauseContext pivotClause() throws RecognitionException {
		PivotClauseContext _localctx = new PivotClauseContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_pivotClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1598);
			match(PIVOT);
			setState(1599);
			match(T__0);
			setState(1600);
			((PivotClauseContext)_localctx).aggregates = namedExpressionSeq();
			setState(1601);
			match(FOR);
			setState(1602);
			pivotColumn();
			setState(1603);
			match(IN);
			setState(1604);
			match(T__0);
			setState(1605);
			((PivotClauseContext)_localctx).pivotValue = pivotValue();
			((PivotClauseContext)_localctx).pivotValues.add(((PivotClauseContext)_localctx).pivotValue);
			setState(1610);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1606);
				match(T__2);
				setState(1607);
				((PivotClauseContext)_localctx).pivotValue = pivotValue();
				((PivotClauseContext)_localctx).pivotValues.add(((PivotClauseContext)_localctx).pivotValue);
				}
				}
				setState(1612);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1613);
			match(T__1);
			setState(1614);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PivotColumnContext extends ParserRuleContext {
		public IdentifierContext identifier;
		public List<IdentifierContext> identifiers = new ArrayList<IdentifierContext>();
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public PivotColumnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pivotColumn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPivotColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPivotColumn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPivotColumn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PivotColumnContext pivotColumn() throws RecognitionException {
		PivotColumnContext _localctx = new PivotColumnContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_pivotColumn);
		int _la;
		try {
			setState(1628);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
			case FROM:
			case ADD:
			case AS:
			case ALL:
			case ANY:
			case DISTINCT:
			case WHERE:
			case GROUP:
			case BY:
			case GROUPING:
			case SETS:
			case CUBE:
			case ROLLUP:
			case ORDER:
			case HAVING:
			case LIMIT:
			case AT:
			case OR:
			case AND:
			case IN:
			case NOT:
			case NO:
			case EXISTS:
			case BETWEEN:
			case LIKE:
			case RLIKE:
			case IS:
			case NULL:
			case TRUE:
			case FALSE:
			case NULLS:
			case ASC:
			case DESC:
			case FOR:
			case INTERVAL:
			case CASE:
			case WHEN:
			case THEN:
			case ELSE:
			case END:
			case JOIN:
			case CROSS:
			case OUTER:
			case INNER:
			case LEFT:
			case SEMI:
			case RIGHT:
			case FULL:
			case NATURAL:
			case ON:
			case PIVOT:
			case LATERAL:
			case WINDOW:
			case OVER:
			case PARTITION:
			case RANGE:
			case ROWS:
			case UNBOUNDED:
			case PRECEDING:
			case FOLLOWING:
			case CURRENT:
			case FIRST:
			case AFTER:
			case LAST:
			case ROW:
			case WITH:
			case VALUES:
			case CREATE:
			case TABLE:
			case DIRECTORY:
			case VIEW:
			case REPLACE:
			case INSERT:
			case DELETE:
			case INTO:
			case DESCRIBE:
			case EXPLAIN:
			case FORMAT:
			case LOGICAL:
			case CODEGEN:
			case COST:
			case CAST:
			case SHOW:
			case TABLES:
			case COLUMNS:
			case COLUMN:
			case USE:
			case PARTITIONS:
			case FUNCTIONS:
			case DROP:
			case UNION:
			case EXCEPT:
			case SETMINUS:
			case INTERSECT:
			case TO:
			case TABLESAMPLE:
			case STRATIFY:
			case ALTER:
			case RENAME:
			case ARRAY:
			case MAP:
			case STRUCT:
			case COMMENT:
			case SET:
			case RESET:
			case DATA:
			case START:
			case TRANSACTION:
			case COMMIT:
			case ROLLBACK:
			case MACRO:
			case IGNORE:
			case BOTH:
			case LEADING:
			case TRAILING:
			case IF:
			case POSITION:
			case EXTRACT:
			case DIV:
			case PERCENTLIT:
			case BUCKET:
			case OUT:
			case OF:
			case SORT:
			case CLUSTER:
			case DISTRIBUTE:
			case OVERWRITE:
			case TRANSFORM:
			case REDUCE:
			case SERDE:
			case SERDEPROPERTIES:
			case RECORDREADER:
			case RECORDWRITER:
			case DELIMITED:
			case FIELDS:
			case TERMINATED:
			case COLLECTION:
			case ITEMS:
			case KEYS:
			case ESCAPED:
			case LINES:
			case SEPARATED:
			case FUNCTION:
			case EXTENDED:
			case REFRESH:
			case CLEAR:
			case CACHE:
			case UNCACHE:
			case LAZY:
			case FORMATTED:
			case GLOBAL:
			case TEMPORARY:
			case OPTIONS:
			case UNSET:
			case TBLPROPERTIES:
			case DBPROPERTIES:
			case BUCKETS:
			case SKEWED:
			case STORED:
			case DIRECTORIES:
			case LOCATION:
			case EXCHANGE:
			case ARCHIVE:
			case UNARCHIVE:
			case FILEFORMAT:
			case TOUCH:
			case COMPACT:
			case CONCATENATE:
			case CHANGE:
			case CASCADE:
			case RESTRICT:
			case CLUSTERED:
			case SORTED:
			case PURGE:
			case INPUTFORMAT:
			case OUTPUTFORMAT:
			case DATABASE:
			case DATABASES:
			case DFS:
			case TRUNCATE:
			case ANALYZE:
			case COMPUTE:
			case LIST:
			case STATISTICS:
			case PARTITIONED:
			case EXTERNAL:
			case DEFINED:
			case REVOKE:
			case GRANT:
			case LOCK:
			case UNLOCK:
			case MSCK:
			case REPAIR:
			case RECOVER:
			case EXPORT:
			case IMPORT:
			case LOAD:
			case ROLE:
			case ROLES:
			case COMPACTIONS:
			case PRINCIPALS:
			case TRANSACTIONS:
			case INDEX:
			case INDEXES:
			case LOCKS:
			case OPTION:
			case ANTI:
			case LOCAL:
			case INPATH:
			case IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1616);
				((PivotColumnContext)_localctx).identifier = identifier();
				((PivotColumnContext)_localctx).identifiers.add(((PivotColumnContext)_localctx).identifier);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(1617);
				match(T__0);
				setState(1618);
				((PivotColumnContext)_localctx).identifier = identifier();
				((PivotColumnContext)_localctx).identifiers.add(((PivotColumnContext)_localctx).identifier);
				setState(1623);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1619);
					match(T__2);
					setState(1620);
					((PivotColumnContext)_localctx).identifier = identifier();
					((PivotColumnContext)_localctx).identifiers.add(((PivotColumnContext)_localctx).identifier);
					}
					}
					setState(1625);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1626);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PivotValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public PivotValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pivotValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPivotValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPivotValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPivotValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PivotValueContext pivotValue() throws RecognitionException {
		PivotValueContext _localctx = new PivotValueContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_pivotValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1630);
			expression();
			setState(1635);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (SELECT - 11)) | (1L << (FROM - 11)) | (1L << (ADD - 11)) | (1L << (AS - 11)) | (1L << (ALL - 11)) | (1L << (ANY - 11)) | (1L << (DISTINCT - 11)) | (1L << (WHERE - 11)) | (1L << (GROUP - 11)) | (1L << (BY - 11)) | (1L << (GROUPING - 11)) | (1L << (SETS - 11)) | (1L << (CUBE - 11)) | (1L << (ROLLUP - 11)) | (1L << (ORDER - 11)) | (1L << (HAVING - 11)) | (1L << (LIMIT - 11)) | (1L << (AT - 11)) | (1L << (OR - 11)) | (1L << (AND - 11)) | (1L << (IN - 11)) | (1L << (NOT - 11)) | (1L << (NO - 11)) | (1L << (EXISTS - 11)) | (1L << (BETWEEN - 11)) | (1L << (LIKE - 11)) | (1L << (RLIKE - 11)) | (1L << (IS - 11)) | (1L << (NULL - 11)) | (1L << (TRUE - 11)) | (1L << (FALSE - 11)) | (1L << (NULLS - 11)) | (1L << (ASC - 11)) | (1L << (DESC - 11)) | (1L << (FOR - 11)) | (1L << (INTERVAL - 11)) | (1L << (CASE - 11)) | (1L << (WHEN - 11)) | (1L << (THEN - 11)) | (1L << (ELSE - 11)) | (1L << (END - 11)) | (1L << (JOIN - 11)) | (1L << (CROSS - 11)) | (1L << (OUTER - 11)) | (1L << (INNER - 11)) | (1L << (LEFT - 11)) | (1L << (SEMI - 11)) | (1L << (RIGHT - 11)) | (1L << (FULL - 11)) | (1L << (NATURAL - 11)) | (1L << (ON - 11)) | (1L << (PIVOT - 11)) | (1L << (LATERAL - 11)) | (1L << (WINDOW - 11)) | (1L << (OVER - 11)) | (1L << (PARTITION - 11)) | (1L << (RANGE - 11)) | (1L << (ROWS - 11)) | (1L << (UNBOUNDED - 11)) | (1L << (PRECEDING - 11)) | (1L << (FOLLOWING - 11)) | (1L << (CURRENT - 11)) | (1L << (FIRST - 11)) | (1L << (AFTER - 11)))) != 0) || ((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & ((1L << (LAST - 75)) | (1L << (ROW - 75)) | (1L << (WITH - 75)) | (1L << (VALUES - 75)) | (1L << (CREATE - 75)) | (1L << (TABLE - 75)) | (1L << (DIRECTORY - 75)) | (1L << (VIEW - 75)) | (1L << (REPLACE - 75)) | (1L << (INSERT - 75)) | (1L << (DELETE - 75)) | (1L << (INTO - 75)) | (1L << (DESCRIBE - 75)) | (1L << (EXPLAIN - 75)) | (1L << (FORMAT - 75)) | (1L << (LOGICAL - 75)) | (1L << (CODEGEN - 75)) | (1L << (COST - 75)) | (1L << (CAST - 75)) | (1L << (SHOW - 75)) | (1L << (TABLES - 75)) | (1L << (COLUMNS - 75)) | (1L << (COLUMN - 75)) | (1L << (USE - 75)) | (1L << (PARTITIONS - 75)) | (1L << (FUNCTIONS - 75)) | (1L << (DROP - 75)) | (1L << (UNION - 75)) | (1L << (EXCEPT - 75)) | (1L << (SETMINUS - 75)) | (1L << (INTERSECT - 75)) | (1L << (TO - 75)) | (1L << (TABLESAMPLE - 75)) | (1L << (STRATIFY - 75)) | (1L << (ALTER - 75)) | (1L << (RENAME - 75)) | (1L << (ARRAY - 75)) | (1L << (MAP - 75)) | (1L << (STRUCT - 75)) | (1L << (COMMENT - 75)) | (1L << (SET - 75)) | (1L << (RESET - 75)) | (1L << (DATA - 75)) | (1L << (START - 75)) | (1L << (TRANSACTION - 75)) | (1L << (COMMIT - 75)) | (1L << (ROLLBACK - 75)) | (1L << (MACRO - 75)) | (1L << (IGNORE - 75)) | (1L << (BOTH - 75)) | (1L << (LEADING - 75)) | (1L << (TRAILING - 75)) | (1L << (IF - 75)) | (1L << (POSITION - 75)) | (1L << (EXTRACT - 75)))) != 0) || ((((_la - 151)) & ~0x3f) == 0 && ((1L << (_la - 151)) & ((1L << (DIV - 151)) | (1L << (PERCENTLIT - 151)) | (1L << (BUCKET - 151)) | (1L << (OUT - 151)) | (1L << (OF - 151)) | (1L << (SORT - 151)) | (1L << (CLUSTER - 151)) | (1L << (DISTRIBUTE - 151)) | (1L << (OVERWRITE - 151)) | (1L << (TRANSFORM - 151)) | (1L << (REDUCE - 151)) | (1L << (SERDE - 151)) | (1L << (SERDEPROPERTIES - 151)) | (1L << (RECORDREADER - 151)) | (1L << (RECORDWRITER - 151)) | (1L << (DELIMITED - 151)) | (1L << (FIELDS - 151)) | (1L << (TERMINATED - 151)) | (1L << (COLLECTION - 151)) | (1L << (ITEMS - 151)) | (1L << (KEYS - 151)) | (1L << (ESCAPED - 151)) | (1L << (LINES - 151)) | (1L << (SEPARATED - 151)) | (1L << (FUNCTION - 151)) | (1L << (EXTENDED - 151)) | (1L << (REFRESH - 151)) | (1L << (CLEAR - 151)) | (1L << (CACHE - 151)) | (1L << (UNCACHE - 151)) | (1L << (LAZY - 151)) | (1L << (FORMATTED - 151)) | (1L << (GLOBAL - 151)) | (1L << (TEMPORARY - 151)) | (1L << (OPTIONS - 151)) | (1L << (UNSET - 151)) | (1L << (TBLPROPERTIES - 151)) | (1L << (DBPROPERTIES - 151)) | (1L << (BUCKETS - 151)) | (1L << (SKEWED - 151)) | (1L << (STORED - 151)) | (1L << (DIRECTORIES - 151)) | (1L << (LOCATION - 151)) | (1L << (EXCHANGE - 151)) | (1L << (ARCHIVE - 151)) | (1L << (UNARCHIVE - 151)) | (1L << (FILEFORMAT - 151)) | (1L << (TOUCH - 151)) | (1L << (COMPACT - 151)) | (1L << (CONCATENATE - 151)) | (1L << (CHANGE - 151)) | (1L << (CASCADE - 151)) | (1L << (RESTRICT - 151)) | (1L << (CLUSTERED - 151)) | (1L << (SORTED - 151)) | (1L << (PURGE - 151)) | (1L << (INPUTFORMAT - 151)) | (1L << (OUTPUTFORMAT - 151)))) != 0) || ((((_la - 215)) & ~0x3f) == 0 && ((1L << (_la - 215)) & ((1L << (DATABASE - 215)) | (1L << (DATABASES - 215)) | (1L << (DFS - 215)) | (1L << (TRUNCATE - 215)) | (1L << (ANALYZE - 215)) | (1L << (COMPUTE - 215)) | (1L << (LIST - 215)) | (1L << (STATISTICS - 215)) | (1L << (PARTITIONED - 215)) | (1L << (EXTERNAL - 215)) | (1L << (DEFINED - 215)) | (1L << (REVOKE - 215)) | (1L << (GRANT - 215)) | (1L << (LOCK - 215)) | (1L << (UNLOCK - 215)) | (1L << (MSCK - 215)) | (1L << (REPAIR - 215)) | (1L << (RECOVER - 215)) | (1L << (EXPORT - 215)) | (1L << (IMPORT - 215)) | (1L << (LOAD - 215)) | (1L << (ROLE - 215)) | (1L << (ROLES - 215)) | (1L << (COMPACTIONS - 215)) | (1L << (PRINCIPALS - 215)) | (1L << (TRANSACTIONS - 215)) | (1L << (INDEX - 215)) | (1L << (INDEXES - 215)) | (1L << (LOCKS - 215)) | (1L << (OPTION - 215)) | (1L << (ANTI - 215)) | (1L << (LOCAL - 215)) | (1L << (INPATH - 215)) | (1L << (IDENTIFIER - 215)) | (1L << (BACKQUOTED_IDENTIFIER - 215)))) != 0)) {
				{
				setState(1632);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,204,_ctx) ) {
				case 1:
					{
					setState(1631);
					match(AS);
					}
					break;
				}
				setState(1634);
				identifier();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LateralViewContext extends ParserRuleContext {
		public IdentifierContext tblName;
		public IdentifierContext identifier;
		public List<IdentifierContext> colName = new ArrayList<IdentifierContext>();
		public TerminalNode LATERAL() { return getToken(NewSqlBaseParser.LATERAL, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode OUTER() { return getToken(NewSqlBaseParser.OUTER, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public LateralViewContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lateralView; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLateralView(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLateralView(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLateralView(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LateralViewContext lateralView() throws RecognitionException {
		LateralViewContext _localctx = new LateralViewContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_lateralView);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1637);
			match(LATERAL);
			setState(1638);
			match(VIEW);
			setState(1640);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,206,_ctx) ) {
			case 1:
				{
				setState(1639);
				match(OUTER);
				}
				break;
			}
			setState(1642);
			qualifiedName();
			setState(1643);
			match(T__0);
			setState(1652);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << JOIN) | (1L << CROSS) | (1L << OUTER) | (1L << INNER) | (1L << LEFT) | (1L << SEMI) | (1L << RIGHT) | (1L << FULL) | (1L << NATURAL) | (1L << ON) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (UNION - 64)) | (1L << (EXCEPT - 64)) | (1L << (SETMINUS - 64)) | (1L << (INTERSECT - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (PLUS - 128)) | (1L << (MINUS - 128)) | (1L << (ASTERISK - 128)) | (1L << (DIV - 128)) | (1L << (TILDE - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (ANTI - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)) | (1L << (STRING - 192)) | (1L << (BIGINT_LITERAL - 192)) | (1L << (SMALLINT_LITERAL - 192)) | (1L << (TINYINT_LITERAL - 192)) | (1L << (INTEGER_VALUE - 192)) | (1L << (DECIMAL_VALUE - 192)) | (1L << (DOUBLE_LITERAL - 192)) | (1L << (BIGDECIMAL_LITERAL - 192)))) != 0) || _la==IDENTIFIER || _la==BACKQUOTED_IDENTIFIER) {
				{
				setState(1644);
				expression();
				setState(1649);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1645);
					match(T__2);
					setState(1646);
					expression();
					}
					}
					setState(1651);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1654);
			match(T__1);
			setState(1655);
			((LateralViewContext)_localctx).tblName = identifier();
			setState(1667);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,211,_ctx) ) {
			case 1:
				{
				setState(1657);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,209,_ctx) ) {
				case 1:
					{
					setState(1656);
					match(AS);
					}
					break;
				}
				setState(1659);
				((LateralViewContext)_localctx).identifier = identifier();
				((LateralViewContext)_localctx).colName.add(((LateralViewContext)_localctx).identifier);
				setState(1664);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,210,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1660);
						match(T__2);
						setState(1661);
						((LateralViewContext)_localctx).identifier = identifier();
						((LateralViewContext)_localctx).colName.add(((LateralViewContext)_localctx).identifier);
						}
						} 
					}
					setState(1666);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,210,_ctx);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetQuantifierContext extends ParserRuleContext {
		public TerminalNode DISTINCT() { return getToken(NewSqlBaseParser.DISTINCT, 0); }
		public TerminalNode ALL() { return getToken(NewSqlBaseParser.ALL, 0); }
		public SetQuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setQuantifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSetQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSetQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSetQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetQuantifierContext setQuantifier() throws RecognitionException {
		SetQuantifierContext _localctx = new SetQuantifierContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_setQuantifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1669);
			_la = _input.LA(1);
			if ( !(_la==ALL || _la==DISTINCT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationContext extends ParserRuleContext {
		public RelationPrimaryContext relationPrimary() {
			return getRuleContext(RelationPrimaryContext.class,0);
		}
		public List<JoinRelationContext> joinRelation() {
			return getRuleContexts(JoinRelationContext.class);
		}
		public JoinRelationContext joinRelation(int i) {
			return getRuleContext(JoinRelationContext.class,i);
		}
		public RelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationContext relation() throws RecognitionException {
		RelationContext _localctx = new RelationContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_relation);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1671);
			relationPrimary();
			setState(1675);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,212,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1672);
					joinRelation();
					}
					} 
				}
				setState(1677);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,212,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JoinRelationContext extends ParserRuleContext {
		public RelationPrimaryContext right;
		public TerminalNode JOIN() { return getToken(NewSqlBaseParser.JOIN, 0); }
		public RelationPrimaryContext relationPrimary() {
			return getRuleContext(RelationPrimaryContext.class,0);
		}
		public JoinTypeContext joinType() {
			return getRuleContext(JoinTypeContext.class,0);
		}
		public JoinCriteriaContext joinCriteria() {
			return getRuleContext(JoinCriteriaContext.class,0);
		}
		public TerminalNode NATURAL() { return getToken(NewSqlBaseParser.NATURAL, 0); }
		public JoinRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterJoinRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitJoinRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitJoinRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinRelationContext joinRelation() throws RecognitionException {
		JoinRelationContext _localctx = new JoinRelationContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_joinRelation);
		try {
			setState(1689);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JOIN:
			case CROSS:
			case INNER:
			case LEFT:
			case RIGHT:
			case FULL:
			case DISTANCE:
			case KNN:
			case ANTI:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(1678);
				joinType();
				}
				setState(1679);
				match(JOIN);
				setState(1680);
				((JoinRelationContext)_localctx).right = relationPrimary();
				setState(1682);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,213,_ctx) ) {
				case 1:
					{
					setState(1681);
					joinCriteria();
					}
					break;
				}
				}
				break;
			case NATURAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(1684);
				match(NATURAL);
				setState(1685);
				joinType();
				setState(1686);
				match(JOIN);
				setState(1687);
				((JoinRelationContext)_localctx).right = relationPrimary();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JoinTypeContext extends ParserRuleContext {
		public TerminalNode INNER() { return getToken(NewSqlBaseParser.INNER, 0); }
		public TerminalNode CROSS() { return getToken(NewSqlBaseParser.CROSS, 0); }
		public TerminalNode LEFT() { return getToken(NewSqlBaseParser.LEFT, 0); }
		public TerminalNode OUTER() { return getToken(NewSqlBaseParser.OUTER, 0); }
		public TerminalNode SEMI() { return getToken(NewSqlBaseParser.SEMI, 0); }
		public TerminalNode RIGHT() { return getToken(NewSqlBaseParser.RIGHT, 0); }
		public TerminalNode FULL() { return getToken(NewSqlBaseParser.FULL, 0); }
		public TerminalNode ANTI() { return getToken(NewSqlBaseParser.ANTI, 0); }
		public TerminalNode DISTANCE() { return getToken(NewSqlBaseParser.DISTANCE, 0); }
		public TerminalNode KNN() { return getToken(NewSqlBaseParser.KNN, 0); }
		public JoinTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterJoinType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitJoinType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitJoinType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinTypeContext joinType() throws RecognitionException {
		JoinTypeContext _localctx = new JoinTypeContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_joinType);
		int _la;
		try {
			setState(1715);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,220,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1692);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INNER) {
					{
					setState(1691);
					match(INNER);
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1694);
				match(CROSS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1695);
				match(LEFT);
				setState(1697);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(1696);
					match(OUTER);
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1699);
				match(LEFT);
				setState(1700);
				match(SEMI);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1701);
				match(RIGHT);
				setState(1703);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(1702);
					match(OUTER);
					}
				}

				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1705);
				match(FULL);
				setState(1707);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(1706);
					match(OUTER);
					}
				}

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1710);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LEFT) {
					{
					setState(1709);
					match(LEFT);
					}
				}

				setState(1712);
				match(ANTI);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1713);
				match(DISTANCE);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1714);
				match(KNN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JoinCriteriaContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode USING() { return getToken(NewSqlBaseParser.USING, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public JoinCriteriaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinCriteria; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterJoinCriteria(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitJoinCriteria(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitJoinCriteria(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinCriteriaContext joinCriteria() throws RecognitionException {
		JoinCriteriaContext _localctx = new JoinCriteriaContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_joinCriteria);
		int _la;
		try {
			setState(1731);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(1717);
				match(ON);
				setState(1718);
				booleanExpression(0);
				}
				break;
			case USING:
				enterOuterAlt(_localctx, 2);
				{
				setState(1719);
				match(USING);
				setState(1720);
				match(T__0);
				setState(1721);
				identifier();
				setState(1726);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1722);
					match(T__2);
					setState(1723);
					identifier();
					}
					}
					setState(1728);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1729);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SampleContext extends ParserRuleContext {
		public TerminalNode TABLESAMPLE() { return getToken(NewSqlBaseParser.TABLESAMPLE, 0); }
		public SampleMethodContext sampleMethod() {
			return getRuleContext(SampleMethodContext.class,0);
		}
		public SampleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sample; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSample(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSample(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSample(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SampleContext sample() throws RecognitionException {
		SampleContext _localctx = new SampleContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_sample);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1733);
			match(TABLESAMPLE);
			setState(1734);
			match(T__0);
			setState(1736);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << JOIN) | (1L << CROSS) | (1L << OUTER) | (1L << INNER) | (1L << LEFT) | (1L << SEMI) | (1L << RIGHT) | (1L << FULL) | (1L << NATURAL) | (1L << ON) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (UNION - 64)) | (1L << (EXCEPT - 64)) | (1L << (SETMINUS - 64)) | (1L << (INTERSECT - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (PLUS - 128)) | (1L << (MINUS - 128)) | (1L << (ASTERISK - 128)) | (1L << (DIV - 128)) | (1L << (TILDE - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (ANTI - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)) | (1L << (STRING - 192)) | (1L << (BIGINT_LITERAL - 192)) | (1L << (SMALLINT_LITERAL - 192)) | (1L << (TINYINT_LITERAL - 192)) | (1L << (INTEGER_VALUE - 192)) | (1L << (DECIMAL_VALUE - 192)) | (1L << (DOUBLE_LITERAL - 192)) | (1L << (BIGDECIMAL_LITERAL - 192)))) != 0) || _la==IDENTIFIER || _la==BACKQUOTED_IDENTIFIER) {
				{
				setState(1735);
				sampleMethod();
				}
			}

			setState(1738);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SampleMethodContext extends ParserRuleContext {
		public SampleMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sampleMethod; }
	 
		public SampleMethodContext() { }
		public void copyFrom(SampleMethodContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SampleByRowsContext extends SampleMethodContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ROWS() { return getToken(NewSqlBaseParser.ROWS, 0); }
		public SampleByRowsContext(SampleMethodContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSampleByRows(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSampleByRows(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSampleByRows(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SampleByPercentileContext extends SampleMethodContext {
		public Token negativeSign;
		public Token percentage;
		public TerminalNode PERCENTLIT() { return getToken(NewSqlBaseParser.PERCENTLIT, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(NewSqlBaseParser.INTEGER_VALUE, 0); }
		public TerminalNode DECIMAL_VALUE() { return getToken(NewSqlBaseParser.DECIMAL_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public SampleByPercentileContext(SampleMethodContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSampleByPercentile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSampleByPercentile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSampleByPercentile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SampleByBucketContext extends SampleMethodContext {
		public Token sampleType;
		public Token numerator;
		public Token denominator;
		public TerminalNode OUT() { return getToken(NewSqlBaseParser.OUT, 0); }
		public TerminalNode OF() { return getToken(NewSqlBaseParser.OF, 0); }
		public TerminalNode BUCKET() { return getToken(NewSqlBaseParser.BUCKET, 0); }
		public List<TerminalNode> INTEGER_VALUE() { return getTokens(NewSqlBaseParser.INTEGER_VALUE); }
		public TerminalNode INTEGER_VALUE(int i) {
			return getToken(NewSqlBaseParser.INTEGER_VALUE, i);
		}
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public SampleByBucketContext(SampleMethodContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSampleByBucket(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSampleByBucket(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSampleByBucket(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SampleByBytesContext extends SampleMethodContext {
		public ExpressionContext bytes;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SampleByBytesContext(SampleMethodContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSampleByBytes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSampleByBytes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSampleByBytes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SampleMethodContext sampleMethod() throws RecognitionException {
		SampleMethodContext _localctx = new SampleMethodContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_sampleMethod);
		int _la;
		try {
			setState(1764);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,227,_ctx) ) {
			case 1:
				_localctx = new SampleByPercentileContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1741);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(1740);
					((SampleByPercentileContext)_localctx).negativeSign = match(MINUS);
					}
				}

				setState(1743);
				((SampleByPercentileContext)_localctx).percentage = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==INTEGER_VALUE || _la==DECIMAL_VALUE) ) {
					((SampleByPercentileContext)_localctx).percentage = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1744);
				match(PERCENTLIT);
				}
				break;
			case 2:
				_localctx = new SampleByRowsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1745);
				expression();
				setState(1746);
				match(ROWS);
				}
				break;
			case 3:
				_localctx = new SampleByBucketContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1748);
				((SampleByBucketContext)_localctx).sampleType = match(BUCKET);
				setState(1749);
				((SampleByBucketContext)_localctx).numerator = match(INTEGER_VALUE);
				setState(1750);
				match(OUT);
				setState(1751);
				match(OF);
				setState(1752);
				((SampleByBucketContext)_localctx).denominator = match(INTEGER_VALUE);
				setState(1761);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ON) {
					{
					setState(1753);
					match(ON);
					setState(1759);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,225,_ctx) ) {
					case 1:
						{
						setState(1754);
						identifier();
						}
						break;
					case 2:
						{
						setState(1755);
						qualifiedName();
						setState(1756);
						match(T__0);
						setState(1757);
						match(T__1);
						}
						break;
					}
					}
				}

				}
				break;
			case 4:
				_localctx = new SampleByBytesContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1763);
				((SampleByBytesContext)_localctx).bytes = expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierListContext extends ParserRuleContext {
		public IdentifierSeqContext identifierSeq() {
			return getRuleContext(IdentifierSeqContext.class,0);
		}
		public IdentifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIdentifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIdentifierList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIdentifierList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierListContext identifierList() throws RecognitionException {
		IdentifierListContext _localctx = new IdentifierListContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_identifierList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1766);
			match(T__0);
			setState(1767);
			identifierSeq();
			setState(1768);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierSeqContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public IdentifierSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIdentifierSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIdentifierSeq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIdentifierSeq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierSeqContext identifierSeq() throws RecognitionException {
		IdentifierSeqContext _localctx = new IdentifierSeqContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_identifierSeq);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1770);
			identifier();
			setState(1775);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,228,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1771);
					match(T__2);
					setState(1772);
					identifier();
					}
					} 
				}
				setState(1777);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,228,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderedIdentifierListContext extends ParserRuleContext {
		public List<OrderedIdentifierContext> orderedIdentifier() {
			return getRuleContexts(OrderedIdentifierContext.class);
		}
		public OrderedIdentifierContext orderedIdentifier(int i) {
			return getRuleContext(OrderedIdentifierContext.class,i);
		}
		public OrderedIdentifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderedIdentifierList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterOrderedIdentifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitOrderedIdentifierList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitOrderedIdentifierList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderedIdentifierListContext orderedIdentifierList() throws RecognitionException {
		OrderedIdentifierListContext _localctx = new OrderedIdentifierListContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_orderedIdentifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1778);
			match(T__0);
			setState(1779);
			orderedIdentifier();
			setState(1784);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1780);
				match(T__2);
				setState(1781);
				orderedIdentifier();
				}
				}
				setState(1786);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1787);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderedIdentifierContext extends ParserRuleContext {
		public Token ordering;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ASC() { return getToken(NewSqlBaseParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(NewSqlBaseParser.DESC, 0); }
		public OrderedIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderedIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterOrderedIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitOrderedIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitOrderedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderedIdentifierContext orderedIdentifier() throws RecognitionException {
		OrderedIdentifierContext _localctx = new OrderedIdentifierContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_orderedIdentifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1789);
			identifier();
			setState(1791);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(1790);
				((OrderedIdentifierContext)_localctx).ordering = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
					((OrderedIdentifierContext)_localctx).ordering = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierCommentListContext extends ParserRuleContext {
		public List<IdentifierCommentContext> identifierComment() {
			return getRuleContexts(IdentifierCommentContext.class);
		}
		public IdentifierCommentContext identifierComment(int i) {
			return getRuleContext(IdentifierCommentContext.class,i);
		}
		public IdentifierCommentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierCommentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIdentifierCommentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIdentifierCommentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIdentifierCommentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierCommentListContext identifierCommentList() throws RecognitionException {
		IdentifierCommentListContext _localctx = new IdentifierCommentListContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_identifierCommentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1793);
			match(T__0);
			setState(1794);
			identifierComment();
			setState(1799);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(1795);
				match(T__2);
				setState(1796);
				identifierComment();
				}
				}
				setState(1801);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1802);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierCommentContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode COMMENT() { return getToken(NewSqlBaseParser.COMMENT, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public IdentifierCommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierComment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIdentifierComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIdentifierComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIdentifierComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierCommentContext identifierComment() throws RecognitionException {
		IdentifierCommentContext _localctx = new IdentifierCommentContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_identifierComment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1804);
			identifier();
			setState(1807);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(1805);
				match(COMMENT);
				setState(1806);
				match(STRING);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationPrimaryContext extends ParserRuleContext {
		public RelationPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationPrimary; }
	 
		public RelationPrimaryContext() { }
		public void copyFrom(RelationPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TableValuedFunctionContext extends RelationPrimaryContext {
		public FunctionTableContext functionTable() {
			return getRuleContext(FunctionTableContext.class,0);
		}
		public TableValuedFunctionContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableValuedFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableValuedFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableValuedFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InlineTableDefault2Context extends RelationPrimaryContext {
		public InlineTableContext inlineTable() {
			return getRuleContext(InlineTableContext.class,0);
		}
		public InlineTableDefault2Context(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInlineTableDefault2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInlineTableDefault2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInlineTableDefault2(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AliasedRelationContext extends RelationPrimaryContext {
		public RelationContext relation() {
			return getRuleContext(RelationContext.class,0);
		}
		public TableAliasContext tableAlias() {
			return getRuleContext(TableAliasContext.class,0);
		}
		public SampleContext sample() {
			return getRuleContext(SampleContext.class,0);
		}
		public AliasedRelationContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAliasedRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAliasedRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAliasedRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AliasedQueryContext extends RelationPrimaryContext {
		public QueryNoWithContext queryNoWith() {
			return getRuleContext(QueryNoWithContext.class,0);
		}
		public TableAliasContext tableAlias() {
			return getRuleContext(TableAliasContext.class,0);
		}
		public SampleContext sample() {
			return getRuleContext(SampleContext.class,0);
		}
		public AliasedQueryContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterAliasedQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitAliasedQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitAliasedQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TableNameContext extends RelationPrimaryContext {
		public TableIdentifierContext tableIdentifier() {
			return getRuleContext(TableIdentifierContext.class,0);
		}
		public TableAliasContext tableAlias() {
			return getRuleContext(TableAliasContext.class,0);
		}
		public SampleContext sample() {
			return getRuleContext(SampleContext.class,0);
		}
		public TableNameContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationPrimaryContext relationPrimary() throws RecognitionException {
		RelationPrimaryContext _localctx = new RelationPrimaryContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_relationPrimary);
		try {
			setState(1833);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,236,_ctx) ) {
			case 1:
				_localctx = new TableNameContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1809);
				tableIdentifier();
				setState(1811);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,233,_ctx) ) {
				case 1:
					{
					setState(1810);
					sample();
					}
					break;
				}
				setState(1813);
				tableAlias();
				}
				break;
			case 2:
				_localctx = new AliasedQueryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1815);
				match(T__0);
				setState(1816);
				queryNoWith();
				setState(1817);
				match(T__1);
				setState(1819);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,234,_ctx) ) {
				case 1:
					{
					setState(1818);
					sample();
					}
					break;
				}
				setState(1821);
				tableAlias();
				}
				break;
			case 3:
				_localctx = new AliasedRelationContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1823);
				match(T__0);
				setState(1824);
				relation();
				setState(1825);
				match(T__1);
				setState(1827);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,235,_ctx) ) {
				case 1:
					{
					setState(1826);
					sample();
					}
					break;
				}
				setState(1829);
				tableAlias();
				}
				break;
			case 4:
				_localctx = new InlineTableDefault2Context(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1831);
				inlineTable();
				}
				break;
			case 5:
				_localctx = new TableValuedFunctionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1832);
				functionTable();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineTableContext extends ParserRuleContext {
		public TerminalNode VALUES() { return getToken(NewSqlBaseParser.VALUES, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TableAliasContext tableAlias() {
			return getRuleContext(TableAliasContext.class,0);
		}
		public InlineTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInlineTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInlineTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInlineTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineTableContext inlineTable() throws RecognitionException {
		InlineTableContext _localctx = new InlineTableContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_inlineTable);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1835);
			match(VALUES);
			setState(1836);
			expression();
			setState(1841);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,237,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1837);
					match(T__2);
					setState(1838);
					expression();
					}
					} 
				}
				setState(1843);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,237,_ctx);
			}
			setState(1844);
			tableAlias();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTableContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TableAliasContext tableAlias() {
			return getRuleContext(TableAliasContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FunctionTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFunctionTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFunctionTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFunctionTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionTableContext functionTable() throws RecognitionException {
		FunctionTableContext _localctx = new FunctionTableContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_functionTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1846);
			identifier();
			setState(1847);
			match(T__0);
			setState(1856);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << JOIN) | (1L << CROSS) | (1L << OUTER) | (1L << INNER) | (1L << LEFT) | (1L << SEMI) | (1L << RIGHT) | (1L << FULL) | (1L << NATURAL) | (1L << ON) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (UNION - 64)) | (1L << (EXCEPT - 64)) | (1L << (SETMINUS - 64)) | (1L << (INTERSECT - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (PLUS - 128)) | (1L << (MINUS - 128)) | (1L << (ASTERISK - 128)) | (1L << (DIV - 128)) | (1L << (TILDE - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (ANTI - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)) | (1L << (STRING - 192)) | (1L << (BIGINT_LITERAL - 192)) | (1L << (SMALLINT_LITERAL - 192)) | (1L << (TINYINT_LITERAL - 192)) | (1L << (INTEGER_VALUE - 192)) | (1L << (DECIMAL_VALUE - 192)) | (1L << (DOUBLE_LITERAL - 192)) | (1L << (BIGDECIMAL_LITERAL - 192)))) != 0) || _la==IDENTIFIER || _la==BACKQUOTED_IDENTIFIER) {
				{
				setState(1848);
				expression();
				setState(1853);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1849);
					match(T__2);
					setState(1850);
					expression();
					}
					}
					setState(1855);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1858);
			match(T__1);
			setState(1859);
			tableAlias();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableAliasContext extends ParserRuleContext {
		public StrictIdentifierContext strictIdentifier() {
			return getRuleContext(StrictIdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TableAliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableAlias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableAlias(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableAlias(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableAliasContext tableAlias() throws RecognitionException {
		TableAliasContext _localctx = new TableAliasContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_tableAlias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1868);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,242,_ctx) ) {
			case 1:
				{
				setState(1862);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,240,_ctx) ) {
				case 1:
					{
					setState(1861);
					match(AS);
					}
					break;
				}
				setState(1864);
				strictIdentifier();
				setState(1866);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,241,_ctx) ) {
				case 1:
					{
					setState(1865);
					identifierList();
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RowFormatContext extends ParserRuleContext {
		public RowFormatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowFormat; }
	 
		public RowFormatContext() { }
		public void copyFrom(RowFormatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RowFormatSerdeContext extends RowFormatContext {
		public Token name;
		public TablePropertyListContext props;
		public TerminalNode ROW() { return getToken(NewSqlBaseParser.ROW, 0); }
		public TerminalNode FORMAT() { return getToken(NewSqlBaseParser.FORMAT, 0); }
		public TerminalNode SERDE() { return getToken(NewSqlBaseParser.SERDE, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public TerminalNode SERDEPROPERTIES() { return getToken(NewSqlBaseParser.SERDEPROPERTIES, 0); }
		public TablePropertyListContext tablePropertyList() {
			return getRuleContext(TablePropertyListContext.class,0);
		}
		public RowFormatSerdeContext(RowFormatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRowFormatSerde(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRowFormatSerde(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRowFormatSerde(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RowFormatDelimitedContext extends RowFormatContext {
		public Token fieldsTerminatedBy;
		public Token escapedBy;
		public Token collectionItemsTerminatedBy;
		public Token keysTerminatedBy;
		public Token linesSeparatedBy;
		public Token nullDefinedAs;
		public TerminalNode ROW() { return getToken(NewSqlBaseParser.ROW, 0); }
		public TerminalNode FORMAT() { return getToken(NewSqlBaseParser.FORMAT, 0); }
		public TerminalNode DELIMITED() { return getToken(NewSqlBaseParser.DELIMITED, 0); }
		public TerminalNode FIELDS() { return getToken(NewSqlBaseParser.FIELDS, 0); }
		public List<TerminalNode> TERMINATED() { return getTokens(NewSqlBaseParser.TERMINATED); }
		public TerminalNode TERMINATED(int i) {
			return getToken(NewSqlBaseParser.TERMINATED, i);
		}
		public List<TerminalNode> BY() { return getTokens(NewSqlBaseParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(NewSqlBaseParser.BY, i);
		}
		public TerminalNode COLLECTION() { return getToken(NewSqlBaseParser.COLLECTION, 0); }
		public TerminalNode ITEMS() { return getToken(NewSqlBaseParser.ITEMS, 0); }
		public TerminalNode MAP() { return getToken(NewSqlBaseParser.MAP, 0); }
		public TerminalNode KEYS() { return getToken(NewSqlBaseParser.KEYS, 0); }
		public TerminalNode LINES() { return getToken(NewSqlBaseParser.LINES, 0); }
		public TerminalNode NULL() { return getToken(NewSqlBaseParser.NULL, 0); }
		public TerminalNode DEFINED() { return getToken(NewSqlBaseParser.DEFINED, 0); }
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public List<TerminalNode> STRING() { return getTokens(NewSqlBaseParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(NewSqlBaseParser.STRING, i);
		}
		public TerminalNode ESCAPED() { return getToken(NewSqlBaseParser.ESCAPED, 0); }
		public RowFormatDelimitedContext(RowFormatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRowFormatDelimited(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRowFormatDelimited(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRowFormatDelimited(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowFormatContext rowFormat() throws RecognitionException {
		RowFormatContext _localctx = new RowFormatContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_rowFormat);
		try {
			setState(1919);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,250,_ctx) ) {
			case 1:
				_localctx = new RowFormatSerdeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1870);
				match(ROW);
				setState(1871);
				match(FORMAT);
				setState(1872);
				match(SERDE);
				setState(1873);
				((RowFormatSerdeContext)_localctx).name = match(STRING);
				setState(1877);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,243,_ctx) ) {
				case 1:
					{
					setState(1874);
					match(WITH);
					setState(1875);
					match(SERDEPROPERTIES);
					setState(1876);
					((RowFormatSerdeContext)_localctx).props = tablePropertyList();
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new RowFormatDelimitedContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1879);
				match(ROW);
				setState(1880);
				match(FORMAT);
				setState(1881);
				match(DELIMITED);
				setState(1891);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,245,_ctx) ) {
				case 1:
					{
					setState(1882);
					match(FIELDS);
					setState(1883);
					match(TERMINATED);
					setState(1884);
					match(BY);
					setState(1885);
					((RowFormatDelimitedContext)_localctx).fieldsTerminatedBy = match(STRING);
					setState(1889);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,244,_ctx) ) {
					case 1:
						{
						setState(1886);
						match(ESCAPED);
						setState(1887);
						match(BY);
						setState(1888);
						((RowFormatDelimitedContext)_localctx).escapedBy = match(STRING);
						}
						break;
					}
					}
					break;
				}
				setState(1898);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,246,_ctx) ) {
				case 1:
					{
					setState(1893);
					match(COLLECTION);
					setState(1894);
					match(ITEMS);
					setState(1895);
					match(TERMINATED);
					setState(1896);
					match(BY);
					setState(1897);
					((RowFormatDelimitedContext)_localctx).collectionItemsTerminatedBy = match(STRING);
					}
					break;
				}
				setState(1905);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,247,_ctx) ) {
				case 1:
					{
					setState(1900);
					match(MAP);
					setState(1901);
					match(KEYS);
					setState(1902);
					match(TERMINATED);
					setState(1903);
					match(BY);
					setState(1904);
					((RowFormatDelimitedContext)_localctx).keysTerminatedBy = match(STRING);
					}
					break;
				}
				setState(1911);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,248,_ctx) ) {
				case 1:
					{
					setState(1907);
					match(LINES);
					setState(1908);
					match(TERMINATED);
					setState(1909);
					match(BY);
					setState(1910);
					((RowFormatDelimitedContext)_localctx).linesSeparatedBy = match(STRING);
					}
					break;
				}
				setState(1917);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,249,_ctx) ) {
				case 1:
					{
					setState(1913);
					match(NULL);
					setState(1914);
					match(DEFINED);
					setState(1915);
					match(AS);
					setState(1916);
					((RowFormatDelimitedContext)_localctx).nullDefinedAs = match(STRING);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableIdentifierContext extends ParserRuleContext {
		public IdentifierContext db;
		public IdentifierContext table;
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TableIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTableIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTableIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTableIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableIdentifierContext tableIdentifier() throws RecognitionException {
		TableIdentifierContext _localctx = new TableIdentifierContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_tableIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1924);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,251,_ctx) ) {
			case 1:
				{
				setState(1921);
				((TableIdentifierContext)_localctx).db = identifier();
				setState(1922);
				match(T__3);
				}
				break;
			}
			setState(1926);
			((TableIdentifierContext)_localctx).table = identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionIdentifierContext extends ParserRuleContext {
		public IdentifierContext db;
		public IdentifierContext function;
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public FunctionIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFunctionIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFunctionIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFunctionIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionIdentifierContext functionIdentifier() throws RecognitionException {
		FunctionIdentifierContext _localctx = new FunctionIdentifierContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_functionIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1931);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,252,_ctx) ) {
			case 1:
				{
				setState(1928);
				((FunctionIdentifierContext)_localctx).db = identifier();
				setState(1929);
				match(T__3);
				}
				break;
			}
			setState(1933);
			((FunctionIdentifierContext)_localctx).function = identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public NamedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNamedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNamedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNamedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedExpressionContext namedExpression() throws RecognitionException {
		NamedExpressionContext _localctx = new NamedExpressionContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_namedExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1935);
			expression();
			setState(1943);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,255,_ctx) ) {
			case 1:
				{
				setState(1937);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,253,_ctx) ) {
				case 1:
					{
					setState(1936);
					match(AS);
					}
					break;
				}
				setState(1941);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SELECT:
				case FROM:
				case ADD:
				case AS:
				case ALL:
				case ANY:
				case DISTINCT:
				case WHERE:
				case GROUP:
				case BY:
				case GROUPING:
				case SETS:
				case CUBE:
				case ROLLUP:
				case ORDER:
				case HAVING:
				case LIMIT:
				case AT:
				case OR:
				case AND:
				case IN:
				case NOT:
				case NO:
				case EXISTS:
				case BETWEEN:
				case LIKE:
				case RLIKE:
				case IS:
				case NULL:
				case TRUE:
				case FALSE:
				case NULLS:
				case ASC:
				case DESC:
				case FOR:
				case INTERVAL:
				case CASE:
				case WHEN:
				case THEN:
				case ELSE:
				case END:
				case JOIN:
				case CROSS:
				case OUTER:
				case INNER:
				case LEFT:
				case SEMI:
				case RIGHT:
				case FULL:
				case NATURAL:
				case ON:
				case PIVOT:
				case LATERAL:
				case WINDOW:
				case OVER:
				case PARTITION:
				case RANGE:
				case ROWS:
				case UNBOUNDED:
				case PRECEDING:
				case FOLLOWING:
				case CURRENT:
				case FIRST:
				case AFTER:
				case LAST:
				case ROW:
				case WITH:
				case VALUES:
				case CREATE:
				case TABLE:
				case DIRECTORY:
				case VIEW:
				case REPLACE:
				case INSERT:
				case DELETE:
				case INTO:
				case DESCRIBE:
				case EXPLAIN:
				case FORMAT:
				case LOGICAL:
				case CODEGEN:
				case COST:
				case CAST:
				case SHOW:
				case TABLES:
				case COLUMNS:
				case COLUMN:
				case USE:
				case PARTITIONS:
				case FUNCTIONS:
				case DROP:
				case UNION:
				case EXCEPT:
				case SETMINUS:
				case INTERSECT:
				case TO:
				case TABLESAMPLE:
				case STRATIFY:
				case ALTER:
				case RENAME:
				case ARRAY:
				case MAP:
				case STRUCT:
				case COMMENT:
				case SET:
				case RESET:
				case DATA:
				case START:
				case TRANSACTION:
				case COMMIT:
				case ROLLBACK:
				case MACRO:
				case IGNORE:
				case BOTH:
				case LEADING:
				case TRAILING:
				case IF:
				case POSITION:
				case EXTRACT:
				case DIV:
				case PERCENTLIT:
				case BUCKET:
				case OUT:
				case OF:
				case SORT:
				case CLUSTER:
				case DISTRIBUTE:
				case OVERWRITE:
				case TRANSFORM:
				case REDUCE:
				case SERDE:
				case SERDEPROPERTIES:
				case RECORDREADER:
				case RECORDWRITER:
				case DELIMITED:
				case FIELDS:
				case TERMINATED:
				case COLLECTION:
				case ITEMS:
				case KEYS:
				case ESCAPED:
				case LINES:
				case SEPARATED:
				case FUNCTION:
				case EXTENDED:
				case REFRESH:
				case CLEAR:
				case CACHE:
				case UNCACHE:
				case LAZY:
				case FORMATTED:
				case GLOBAL:
				case TEMPORARY:
				case OPTIONS:
				case UNSET:
				case TBLPROPERTIES:
				case DBPROPERTIES:
				case BUCKETS:
				case SKEWED:
				case STORED:
				case DIRECTORIES:
				case LOCATION:
				case EXCHANGE:
				case ARCHIVE:
				case UNARCHIVE:
				case FILEFORMAT:
				case TOUCH:
				case COMPACT:
				case CONCATENATE:
				case CHANGE:
				case CASCADE:
				case RESTRICT:
				case CLUSTERED:
				case SORTED:
				case PURGE:
				case INPUTFORMAT:
				case OUTPUTFORMAT:
				case DATABASE:
				case DATABASES:
				case DFS:
				case TRUNCATE:
				case ANALYZE:
				case COMPUTE:
				case LIST:
				case STATISTICS:
				case PARTITIONED:
				case EXTERNAL:
				case DEFINED:
				case REVOKE:
				case GRANT:
				case LOCK:
				case UNLOCK:
				case MSCK:
				case REPAIR:
				case RECOVER:
				case EXPORT:
				case IMPORT:
				case LOAD:
				case ROLE:
				case ROLES:
				case COMPACTIONS:
				case PRINCIPALS:
				case TRANSACTIONS:
				case INDEX:
				case INDEXES:
				case LOCKS:
				case OPTION:
				case ANTI:
				case LOCAL:
				case INPATH:
				case IDENTIFIER:
				case BACKQUOTED_IDENTIFIER:
					{
					setState(1939);
					identifier();
					}
					break;
				case T__0:
					{
					setState(1940);
					identifierList();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedExpressionSeqContext extends ParserRuleContext {
		public List<NamedExpressionContext> namedExpression() {
			return getRuleContexts(NamedExpressionContext.class);
		}
		public NamedExpressionContext namedExpression(int i) {
			return getRuleContext(NamedExpressionContext.class,i);
		}
		public NamedExpressionSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedExpressionSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNamedExpressionSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNamedExpressionSeq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNamedExpressionSeq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedExpressionSeqContext namedExpressionSeq() throws RecognitionException {
		NamedExpressionSeqContext _localctx = new NamedExpressionSeqContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_namedExpressionSeq);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1945);
			namedExpression();
			setState(1950);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,256,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1946);
					match(T__2);
					setState(1947);
					namedExpression();
					}
					} 
				}
				setState(1952);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,256,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1953);
			booleanExpression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpressionContext extends ParserRuleContext {
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
	 
		public BooleanExpressionContext() { }
		public void copyFrom(BooleanExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LogicalNotContext extends BooleanExpressionContext {
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public LogicalNotContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLogicalNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLogicalNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLogicalNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicatedContext extends BooleanExpressionContext {
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public PredicatedContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPredicated(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPredicated(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPredicated(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExistsContext extends BooleanExpressionContext {
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public ExistsContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitExists(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitExists(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalBinaryContext extends BooleanExpressionContext {
		public BooleanExpressionContext left;
		public Token operator;
		public BooleanExpressionContext right;
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(NewSqlBaseParser.AND, 0); }
		public TerminalNode OR() { return getToken(NewSqlBaseParser.OR, 0); }
		public LogicalBinaryContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLogicalBinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLogicalBinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLogicalBinary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		return booleanExpression(0);
	}

	private BooleanExpressionContext booleanExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, _parentState);
		BooleanExpressionContext _prevctx = _localctx;
		int _startState = 150;
		enterRecursionRule(_localctx, 150, RULE_booleanExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1967);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,258,_ctx) ) {
			case 1:
				{
				_localctx = new LogicalNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1956);
				match(NOT);
				setState(1957);
				booleanExpression(5);
				}
				break;
			case 2:
				{
				_localctx = new ExistsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1958);
				match(EXISTS);
				setState(1959);
				match(T__0);
				setState(1960);
				query();
				setState(1961);
				match(T__1);
				}
				break;
			case 3:
				{
				_localctx = new PredicatedContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1963);
				valueExpression(0);
				setState(1965);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,257,_ctx) ) {
				case 1:
					{
					setState(1964);
					predicate();
					}
					break;
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1977);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,260,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1975);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,259,_ctx) ) {
					case 1:
						{
						_localctx = new LogicalBinaryContext(new BooleanExpressionContext(_parentctx, _parentState));
						((LogicalBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpression);
						setState(1969);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1970);
						((LogicalBinaryContext)_localctx).operator = match(AND);
						setState(1971);
						((LogicalBinaryContext)_localctx).right = booleanExpression(3);
						}
						break;
					case 2:
						{
						_localctx = new LogicalBinaryContext(new BooleanExpressionContext(_parentctx, _parentState));
						((LogicalBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpression);
						setState(1972);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1973);
						((LogicalBinaryContext)_localctx).operator = match(OR);
						setState(1974);
						((LogicalBinaryContext)_localctx).right = booleanExpression(2);
						}
						break;
					}
					} 
				}
				setState(1979);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,260,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public Token kind;
		public ValueExpressionContext lower;
		public ValueExpressionContext upper;
		public ExpressionContext expression;
		public List<ExpressionContext> argument = new ArrayList<ExpressionContext>();
		public ValueExpressionContext pattern;
		public ValueExpressionContext right;
		public TerminalNode AND() { return getToken(NewSqlBaseParser.AND, 0); }
		public TerminalNode BETWEEN() { return getToken(NewSqlBaseParser.BETWEEN, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode RLIKE() { return getToken(NewSqlBaseParser.RLIKE, 0); }
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public TerminalNode IS() { return getToken(NewSqlBaseParser.IS, 0); }
		public TerminalNode NULL() { return getToken(NewSqlBaseParser.NULL, 0); }
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public TerminalNode DISTINCT() { return getToken(NewSqlBaseParser.DISTINCT, 0); }
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_predicate);
		int _la;
		try {
			setState(2040);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,269,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1981);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1980);
					match(NOT);
					}
				}

				setState(1983);
				((PredicateContext)_localctx).kind = match(BETWEEN);
				setState(1984);
				((PredicateContext)_localctx).lower = valueExpression(0);
				setState(1985);
				match(AND);
				setState(1986);
				((PredicateContext)_localctx).upper = valueExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1989);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1988);
					match(NOT);
					}
				}

				setState(1991);
				((PredicateContext)_localctx).kind = match(IN);
				setState(1992);
				match(T__0);
				setState(1993);
				expression();
				setState(1998);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(1994);
					match(T__2);
					setState(1995);
					expression();
					}
					}
					setState(2000);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2001);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2004);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2003);
					match(NOT);
					}
				}

				setState(2006);
				((PredicateContext)_localctx).kind = match(IN);
				setState(2007);
				qualifiedName();
				setState(2008);
				match(T__0);
				{
				setState(2009);
				((PredicateContext)_localctx).expression = expression();
				((PredicateContext)_localctx).argument.add(((PredicateContext)_localctx).expression);
				setState(2010);
				match(T__2);
				setState(2011);
				((PredicateContext)_localctx).expression = expression();
				((PredicateContext)_localctx).argument.add(((PredicateContext)_localctx).expression);
				}
				setState(2013);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2016);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2015);
					match(NOT);
					}
				}

				setState(2018);
				((PredicateContext)_localctx).kind = match(IN);
				setState(2019);
				match(T__0);
				setState(2020);
				query();
				setState(2021);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2024);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2023);
					match(NOT);
					}
				}

				setState(2026);
				((PredicateContext)_localctx).kind = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==LIKE || _la==RLIKE) ) {
					((PredicateContext)_localctx).kind = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2027);
				((PredicateContext)_localctx).pattern = valueExpression(0);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(2028);
				match(IS);
				setState(2030);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2029);
					match(NOT);
					}
				}

				setState(2032);
				((PredicateContext)_localctx).kind = match(NULL);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(2033);
				match(IS);
				setState(2035);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2034);
					match(NOT);
					}
				}

				setState(2037);
				((PredicateContext)_localctx).kind = match(DISTINCT);
				setState(2038);
				match(FROM);
				setState(2039);
				((PredicateContext)_localctx).right = valueExpression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueExpressionContext extends ParserRuleContext {
		public ValueExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueExpression; }
	 
		public ValueExpressionContext() { }
		public void copyFrom(ValueExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ValueExpressionDefaultContext extends ValueExpressionContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public ValueExpressionDefaultContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterValueExpressionDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitValueExpressionDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitValueExpressionDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparisonContext extends ValueExpressionContext {
		public ValueExpressionContext left;
		public ValueExpressionContext right;
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public ComparisonContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArithmeticBinaryContext extends ValueExpressionContext {
		public ValueExpressionContext left;
		public Token operator;
		public ValueExpressionContext right;
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode ASTERISK() { return getToken(NewSqlBaseParser.ASTERISK, 0); }
		public TerminalNode SLASH() { return getToken(NewSqlBaseParser.SLASH, 0); }
		public TerminalNode PERCENT() { return getToken(NewSqlBaseParser.PERCENT, 0); }
		public TerminalNode DIV() { return getToken(NewSqlBaseParser.DIV, 0); }
		public TerminalNode PLUS() { return getToken(NewSqlBaseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public TerminalNode CONCAT_PIPE() { return getToken(NewSqlBaseParser.CONCAT_PIPE, 0); }
		public TerminalNode AMPERSAND() { return getToken(NewSqlBaseParser.AMPERSAND, 0); }
		public TerminalNode HAT() { return getToken(NewSqlBaseParser.HAT, 0); }
		public TerminalNode PIPE() { return getToken(NewSqlBaseParser.PIPE, 0); }
		public ArithmeticBinaryContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterArithmeticBinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitArithmeticBinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitArithmeticBinary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArithmeticUnaryContext extends ValueExpressionContext {
		public Token operator;
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(NewSqlBaseParser.PLUS, 0); }
		public TerminalNode TILDE() { return getToken(NewSqlBaseParser.TILDE, 0); }
		public ArithmeticUnaryContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterArithmeticUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitArithmeticUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitArithmeticUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueExpressionContext valueExpression() throws RecognitionException {
		return valueExpression(0);
	}

	private ValueExpressionContext valueExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ValueExpressionContext _localctx = new ValueExpressionContext(_ctx, _parentState);
		ValueExpressionContext _prevctx = _localctx;
		int _startState = 154;
		enterRecursionRule(_localctx, 154, RULE_valueExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2046);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,270,_ctx) ) {
			case 1:
				{
				_localctx = new ValueExpressionDefaultContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(2043);
				primaryExpression(0);
				}
				break;
			case 2:
				{
				_localctx = new ArithmeticUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2044);
				((ArithmeticUnaryContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 146)) & ~0x3f) == 0 && ((1L << (_la - 146)) & ((1L << (PLUS - 146)) | (1L << (MINUS - 146)) | (1L << (TILDE - 146)))) != 0)) ) {
					((ArithmeticUnaryContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2045);
				valueExpression(7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(2069);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,272,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(2067);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,271,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(2048);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(2049);
						((ArithmeticBinaryContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 148)) & ~0x3f) == 0 && ((1L << (_la - 148)) & ((1L << (ASTERISK - 148)) | (1L << (SLASH - 148)) | (1L << (PERCENT - 148)) | (1L << (DIV - 148)))) != 0)) ) {
							((ArithmeticBinaryContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2050);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(7);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(2051);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(2052);
						((ArithmeticBinaryContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 146)) & ~0x3f) == 0 && ((1L << (_la - 146)) & ((1L << (PLUS - 146)) | (1L << (MINUS - 146)) | (1L << (CONCAT_PIPE - 146)))) != 0)) ) {
							((ArithmeticBinaryContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2053);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(6);
						}
						break;
					case 3:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(2054);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(2055);
						((ArithmeticBinaryContext)_localctx).operator = match(AMPERSAND);
						setState(2056);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(5);
						}
						break;
					case 4:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(2057);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(2058);
						((ArithmeticBinaryContext)_localctx).operator = match(HAT);
						setState(2059);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(4);
						}
						break;
					case 5:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(2060);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(2061);
						((ArithmeticBinaryContext)_localctx).operator = match(PIPE);
						setState(2062);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(3);
						}
						break;
					case 6:
						{
						_localctx = new ComparisonContext(new ValueExpressionContext(_parentctx, _parentState));
						((ComparisonContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(2063);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(2064);
						comparisonOperator();
						setState(2065);
						((ComparisonContext)_localctx).right = valueExpression(2);
						}
						break;
					}
					} 
				}
				setState(2071);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,272,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
	 
		public PrimaryExpressionContext() { }
		public void copyFrom(PrimaryExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StructContext extends PrimaryExpressionContext {
		public NamedExpressionContext namedExpression;
		public List<NamedExpressionContext> argument = new ArrayList<NamedExpressionContext>();
		public TerminalNode STRUCT() { return getToken(NewSqlBaseParser.STRUCT, 0); }
		public List<NamedExpressionContext> namedExpression() {
			return getRuleContexts(NamedExpressionContext.class);
		}
		public NamedExpressionContext namedExpression(int i) {
			return getRuleContext(NamedExpressionContext.class,i);
		}
		public StructContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterStruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitStruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitStruct(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DereferenceContext extends PrimaryExpressionContext {
		public PrimaryExpressionContext base;
		public IdentifierContext fieldName;
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DereferenceContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDereference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDereference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDereference(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SimpleCaseContext extends PrimaryExpressionContext {
		public ExpressionContext value;
		public ExpressionContext elseExpression;
		public TerminalNode CASE() { return getToken(NewSqlBaseParser.CASE, 0); }
		public TerminalNode END() { return getToken(NewSqlBaseParser.END, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<WhenClauseContext> whenClause() {
			return getRuleContexts(WhenClauseContext.class);
		}
		public WhenClauseContext whenClause(int i) {
			return getRuleContext(WhenClauseContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(NewSqlBaseParser.ELSE, 0); }
		public SimpleCaseContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSimpleCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSimpleCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSimpleCase(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ColumnReferenceContext extends PrimaryExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ColumnReferenceContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterColumnReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitColumnReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitColumnReference(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RowConstructorContext extends PrimaryExpressionContext {
		public List<NamedExpressionContext> namedExpression() {
			return getRuleContexts(NamedExpressionContext.class);
		}
		public NamedExpressionContext namedExpression(int i) {
			return getRuleContext(NamedExpressionContext.class,i);
		}
		public RowConstructorContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterRowConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitRowConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitRowConstructor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LastContext extends PrimaryExpressionContext {
		public TerminalNode LAST() { return getToken(NewSqlBaseParser.LAST, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IGNORE() { return getToken(NewSqlBaseParser.IGNORE, 0); }
		public TerminalNode NULLS() { return getToken(NewSqlBaseParser.NULLS, 0); }
		public LastContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLast(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLast(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StarContext extends PrimaryExpressionContext {
		public TerminalNode ASTERISK() { return getToken(NewSqlBaseParser.ASTERISK, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public StarContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterStar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitStar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitStar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubscriptContext extends PrimaryExpressionContext {
		public PrimaryExpressionContext value;
		public ValueExpressionContext index;
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public SubscriptContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSubscript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSubscript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSubscript(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubqueryExpressionContext extends PrimaryExpressionContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public SubqueryExpressionContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSubqueryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSubqueryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSubqueryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CastContext extends PrimaryExpressionContext {
		public TerminalNode CAST() { return getToken(NewSqlBaseParser.CAST, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public CastContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterCast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitCast(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitCast(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstantDefaultContext extends PrimaryExpressionContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ConstantDefaultContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterConstantDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitConstantDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitConstantDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LambdaContext extends PrimaryExpressionContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(NewSqlBaseParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(NewSqlBaseParser.IDENTIFIER, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LambdaContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterLambda(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitLambda(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitLambda(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesizedExpressionContext extends PrimaryExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenthesizedExpressionContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitParenthesizedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitParenthesizedExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExtractContext extends PrimaryExpressionContext {
		public IdentifierContext field;
		public ValueExpressionContext source;
		public TerminalNode EXTRACT() { return getToken(NewSqlBaseParser.EXTRACT, 0); }
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public ExtractContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterExtract(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitExtract(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitExtract(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionCallContext extends PrimaryExpressionContext {
		public ExpressionContext expression;
		public List<ExpressionContext> argument = new ArrayList<ExpressionContext>();
		public Token trimOption;
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode OVER() { return getToken(NewSqlBaseParser.OVER, 0); }
		public WindowSpecContext windowSpec() {
			return getRuleContext(WindowSpecContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public TerminalNode BOTH() { return getToken(NewSqlBaseParser.BOTH, 0); }
		public TerminalNode LEADING() { return getToken(NewSqlBaseParser.LEADING, 0); }
		public TerminalNode TRAILING() { return getToken(NewSqlBaseParser.TRAILING, 0); }
		public FunctionCallContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SearchedCaseContext extends PrimaryExpressionContext {
		public ExpressionContext elseExpression;
		public TerminalNode CASE() { return getToken(NewSqlBaseParser.CASE, 0); }
		public TerminalNode END() { return getToken(NewSqlBaseParser.END, 0); }
		public List<WhenClauseContext> whenClause() {
			return getRuleContexts(WhenClauseContext.class);
		}
		public WhenClauseContext whenClause(int i) {
			return getRuleContext(WhenClauseContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(NewSqlBaseParser.ELSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SearchedCaseContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSearchedCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSearchedCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSearchedCase(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PositionContext extends PrimaryExpressionContext {
		public ValueExpressionContext substr;
		public ValueExpressionContext str;
		public TerminalNode POSITION() { return getToken(NewSqlBaseParser.POSITION, 0); }
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public PositionContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FirstContext extends PrimaryExpressionContext {
		public TerminalNode FIRST() { return getToken(NewSqlBaseParser.FIRST, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IGNORE() { return getToken(NewSqlBaseParser.IGNORE, 0); }
		public TerminalNode NULLS() { return getToken(NewSqlBaseParser.NULLS, 0); }
		public FirstContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFirst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFirst(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFirst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		return primaryExpression(0);
	}

	private PrimaryExpressionContext primaryExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, _parentState);
		PrimaryExpressionContext _prevctx = _localctx;
		int _startState = 156;
		enterRecursionRule(_localctx, 156, RULE_primaryExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,287,_ctx) ) {
			case 1:
				{
				_localctx = new SearchedCaseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(2073);
				match(CASE);
				setState(2075); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2074);
					whenClause();
					}
					}
					setState(2077); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(2081);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(2079);
					match(ELSE);
					setState(2080);
					((SearchedCaseContext)_localctx).elseExpression = expression();
					}
				}

				setState(2083);
				match(END);
				}
				break;
			case 2:
				{
				_localctx = new SimpleCaseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2085);
				match(CASE);
				setState(2086);
				((SimpleCaseContext)_localctx).value = expression();
				setState(2088); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2087);
					whenClause();
					}
					}
					setState(2090); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(2094);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(2092);
					match(ELSE);
					setState(2093);
					((SimpleCaseContext)_localctx).elseExpression = expression();
					}
				}

				setState(2096);
				match(END);
				}
				break;
			case 3:
				{
				_localctx = new CastContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2098);
				match(CAST);
				setState(2099);
				match(T__0);
				setState(2100);
				expression();
				setState(2101);
				match(AS);
				setState(2102);
				dataType();
				setState(2103);
				match(T__1);
				}
				break;
			case 4:
				{
				_localctx = new StructContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2105);
				match(STRUCT);
				setState(2106);
				match(T__0);
				setState(2115);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << JOIN) | (1L << CROSS) | (1L << OUTER) | (1L << INNER) | (1L << LEFT) | (1L << SEMI) | (1L << RIGHT) | (1L << FULL) | (1L << NATURAL) | (1L << ON) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (UNION - 64)) | (1L << (EXCEPT - 64)) | (1L << (SETMINUS - 64)) | (1L << (INTERSECT - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (PLUS - 128)) | (1L << (MINUS - 128)) | (1L << (ASTERISK - 128)) | (1L << (DIV - 128)) | (1L << (TILDE - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (ANTI - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)) | (1L << (STRING - 192)) | (1L << (BIGINT_LITERAL - 192)) | (1L << (SMALLINT_LITERAL - 192)) | (1L << (TINYINT_LITERAL - 192)) | (1L << (INTEGER_VALUE - 192)) | (1L << (DECIMAL_VALUE - 192)) | (1L << (DOUBLE_LITERAL - 192)) | (1L << (BIGDECIMAL_LITERAL - 192)))) != 0) || _la==IDENTIFIER || _la==BACKQUOTED_IDENTIFIER) {
					{
					setState(2107);
					((StructContext)_localctx).namedExpression = namedExpression();
					((StructContext)_localctx).argument.add(((StructContext)_localctx).namedExpression);
					setState(2112);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(2108);
						match(T__2);
						setState(2109);
						((StructContext)_localctx).namedExpression = namedExpression();
						((StructContext)_localctx).argument.add(((StructContext)_localctx).namedExpression);
						}
						}
						setState(2114);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(2117);
				match(T__1);
				}
				break;
			case 5:
				{
				_localctx = new FirstContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2118);
				match(FIRST);
				setState(2119);
				match(T__0);
				setState(2120);
				expression();
				setState(2123);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IGNORE) {
					{
					setState(2121);
					match(IGNORE);
					setState(2122);
					match(NULLS);
					}
				}

				setState(2125);
				match(T__1);
				}
				break;
			case 6:
				{
				_localctx = new LastContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2127);
				match(LAST);
				setState(2128);
				match(T__0);
				setState(2129);
				expression();
				setState(2132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IGNORE) {
					{
					setState(2130);
					match(IGNORE);
					setState(2131);
					match(NULLS);
					}
				}

				setState(2134);
				match(T__1);
				}
				break;
			case 7:
				{
				_localctx = new PositionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2136);
				match(POSITION);
				setState(2137);
				match(T__0);
				setState(2138);
				((PositionContext)_localctx).substr = valueExpression(0);
				setState(2139);
				match(IN);
				setState(2140);
				((PositionContext)_localctx).str = valueExpression(0);
				setState(2141);
				match(T__1);
				}
				break;
			case 8:
				{
				_localctx = new ConstantDefaultContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2143);
				constant();
				}
				break;
			case 9:
				{
				_localctx = new StarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2144);
				match(ASTERISK);
				}
				break;
			case 10:
				{
				_localctx = new StarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2145);
				qualifiedName();
				setState(2146);
				match(T__3);
				setState(2147);
				match(ASTERISK);
				}
				break;
			case 11:
				{
				_localctx = new RowConstructorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2149);
				match(T__0);
				setState(2150);
				namedExpression();
				setState(2153); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2151);
					match(T__2);
					setState(2152);
					namedExpression();
					}
					}
					setState(2155); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__2 );
				setState(2157);
				match(T__1);
				}
				break;
			case 12:
				{
				_localctx = new SubqueryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2159);
				match(T__0);
				setState(2160);
				query();
				setState(2161);
				match(T__1);
				}
				break;
			case 13:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2163);
				qualifiedName();
				setState(2164);
				match(T__0);
				setState(2176);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << JOIN) | (1L << CROSS) | (1L << OUTER) | (1L << INNER) | (1L << LEFT) | (1L << SEMI) | (1L << RIGHT) | (1L << FULL) | (1L << NATURAL) | (1L << ON) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (UNION - 64)) | (1L << (EXCEPT - 64)) | (1L << (SETMINUS - 64)) | (1L << (INTERSECT - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (PLUS - 128)) | (1L << (MINUS - 128)) | (1L << (ASTERISK - 128)) | (1L << (DIV - 128)) | (1L << (TILDE - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (ANTI - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)) | (1L << (STRING - 192)) | (1L << (BIGINT_LITERAL - 192)) | (1L << (SMALLINT_LITERAL - 192)) | (1L << (TINYINT_LITERAL - 192)) | (1L << (INTEGER_VALUE - 192)) | (1L << (DECIMAL_VALUE - 192)) | (1L << (DOUBLE_LITERAL - 192)) | (1L << (BIGDECIMAL_LITERAL - 192)))) != 0) || _la==IDENTIFIER || _la==BACKQUOTED_IDENTIFIER) {
					{
					setState(2166);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,282,_ctx) ) {
					case 1:
						{
						setState(2165);
						setQuantifier();
						}
						break;
					}
					setState(2168);
					((FunctionCallContext)_localctx).expression = expression();
					((FunctionCallContext)_localctx).argument.add(((FunctionCallContext)_localctx).expression);
					setState(2173);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(2169);
						match(T__2);
						setState(2170);
						((FunctionCallContext)_localctx).expression = expression();
						((FunctionCallContext)_localctx).argument.add(((FunctionCallContext)_localctx).expression);
						}
						}
						setState(2175);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(2178);
				match(T__1);
				setState(2181);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,285,_ctx) ) {
				case 1:
					{
					setState(2179);
					match(OVER);
					setState(2180);
					windowSpec();
					}
					break;
				}
				}
				break;
			case 14:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2183);
				qualifiedName();
				setState(2184);
				match(T__0);
				setState(2185);
				((FunctionCallContext)_localctx).trimOption = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 132)) & ~0x3f) == 0 && ((1L << (_la - 132)) & ((1L << (BOTH - 132)) | (1L << (LEADING - 132)) | (1L << (TRAILING - 132)))) != 0)) ) {
					((FunctionCallContext)_localctx).trimOption = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2186);
				((FunctionCallContext)_localctx).expression = expression();
				((FunctionCallContext)_localctx).argument.add(((FunctionCallContext)_localctx).expression);
				setState(2187);
				match(FROM);
				setState(2188);
				((FunctionCallContext)_localctx).expression = expression();
				((FunctionCallContext)_localctx).argument.add(((FunctionCallContext)_localctx).expression);
				setState(2189);
				match(T__1);
				}
				break;
			case 15:
				{
				_localctx = new LambdaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2191);
				match(IDENTIFIER);
				setState(2192);
				match(T__6);
				setState(2193);
				expression();
				}
				break;
			case 16:
				{
				_localctx = new LambdaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2194);
				match(T__0);
				setState(2195);
				match(IDENTIFIER);
				setState(2198); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2196);
					match(T__2);
					setState(2197);
					match(IDENTIFIER);
					}
					}
					setState(2200); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__2 );
				setState(2202);
				match(T__1);
				setState(2203);
				match(T__6);
				setState(2204);
				expression();
				}
				break;
			case 17:
				{
				_localctx = new ColumnReferenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2205);
				identifier();
				}
				break;
			case 18:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2206);
				match(T__0);
				setState(2207);
				expression();
				setState(2208);
				match(T__1);
				}
				break;
			case 19:
				{
				_localctx = new ExtractContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2210);
				match(EXTRACT);
				setState(2211);
				match(T__0);
				setState(2212);
				((ExtractContext)_localctx).field = identifier();
				setState(2213);
				match(FROM);
				setState(2214);
				((ExtractContext)_localctx).source = valueExpression(0);
				setState(2215);
				match(T__1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(2229);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,289,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(2227);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,288,_ctx) ) {
					case 1:
						{
						_localctx = new SubscriptContext(new PrimaryExpressionContext(_parentctx, _parentState));
						((SubscriptContext)_localctx).value = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpression);
						setState(2219);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(2220);
						match(T__7);
						setState(2221);
						((SubscriptContext)_localctx).index = valueExpression(0);
						setState(2222);
						match(T__8);
						}
						break;
					case 2:
						{
						_localctx = new DereferenceContext(new PrimaryExpressionContext(_parentctx, _parentState));
						((DereferenceContext)_localctx).base = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpression);
						setState(2224);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(2225);
						match(T__3);
						setState(2226);
						((DereferenceContext)_localctx).fieldName = identifier();
						}
						break;
					}
					} 
				}
				setState(2231);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,289,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
	 
		public ConstantContext() { }
		public void copyFrom(ConstantContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NullLiteralContext extends ConstantContext {
		public TerminalNode NULL() { return getToken(NewSqlBaseParser.NULL, 0); }
		public NullLiteralContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNullLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNullLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringLiteralContext extends ConstantContext {
		public List<TerminalNode> STRING() { return getTokens(NewSqlBaseParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(NewSqlBaseParser.STRING, i);
		}
		public StringLiteralContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypeConstructorContext extends ConstantContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public TypeConstructorContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTypeConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTypeConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTypeConstructor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntervalLiteralContext extends ConstantContext {
		public IntervalContext interval() {
			return getRuleContext(IntervalContext.class,0);
		}
		public IntervalLiteralContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIntervalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIntervalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIntervalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumericLiteralContext extends ConstantContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public NumericLiteralContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNumericLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNumericLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanLiteralContext extends ConstantContext {
		public BooleanValueContext booleanValue() {
			return getRuleContext(BooleanValueContext.class,0);
		}
		public BooleanLiteralContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitBooleanLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_constant);
		try {
			int _alt;
			setState(2244);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,291,_ctx) ) {
			case 1:
				_localctx = new NullLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2232);
				match(NULL);
				}
				break;
			case 2:
				_localctx = new IntervalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2233);
				interval();
				}
				break;
			case 3:
				_localctx = new TypeConstructorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2234);
				identifier();
				setState(2235);
				match(STRING);
				}
				break;
			case 4:
				_localctx = new NumericLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2237);
				number();
				}
				break;
			case 5:
				_localctx = new BooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2238);
				booleanValue();
				}
				break;
			case 6:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(2240); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(2239);
						match(STRING);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(2242); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,290,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonOperatorContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(NewSqlBaseParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(NewSqlBaseParser.NEQ, 0); }
		public TerminalNode NEQJ() { return getToken(NewSqlBaseParser.NEQJ, 0); }
		public TerminalNode LT() { return getToken(NewSqlBaseParser.LT, 0); }
		public TerminalNode LTE() { return getToken(NewSqlBaseParser.LTE, 0); }
		public TerminalNode GT() { return getToken(NewSqlBaseParser.GT, 0); }
		public TerminalNode GTE() { return getToken(NewSqlBaseParser.GTE, 0); }
		public TerminalNode NSEQ() { return getToken(NewSqlBaseParser.NSEQ, 0); }
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitComparisonOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2246);
			_la = _input.LA(1);
			if ( !(((((_la - 138)) & ~0x3f) == 0 && ((1L << (_la - 138)) & ((1L << (EQ - 138)) | (1L << (NSEQ - 138)) | (1L << (NEQ - 138)) | (1L << (NEQJ - 138)) | (1L << (LT - 138)) | (1L << (LTE - 138)) | (1L << (GT - 138)) | (1L << (GTE - 138)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticOperatorContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(NewSqlBaseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public TerminalNode ASTERISK() { return getToken(NewSqlBaseParser.ASTERISK, 0); }
		public TerminalNode SLASH() { return getToken(NewSqlBaseParser.SLASH, 0); }
		public TerminalNode PERCENT() { return getToken(NewSqlBaseParser.PERCENT, 0); }
		public TerminalNode DIV() { return getToken(NewSqlBaseParser.DIV, 0); }
		public TerminalNode TILDE() { return getToken(NewSqlBaseParser.TILDE, 0); }
		public TerminalNode AMPERSAND() { return getToken(NewSqlBaseParser.AMPERSAND, 0); }
		public TerminalNode PIPE() { return getToken(NewSqlBaseParser.PIPE, 0); }
		public TerminalNode CONCAT_PIPE() { return getToken(NewSqlBaseParser.CONCAT_PIPE, 0); }
		public TerminalNode HAT() { return getToken(NewSqlBaseParser.HAT, 0); }
		public ArithmeticOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterArithmeticOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitArithmeticOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitArithmeticOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticOperatorContext arithmeticOperator() throws RecognitionException {
		ArithmeticOperatorContext _localctx = new ArithmeticOperatorContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_arithmeticOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2248);
			_la = _input.LA(1);
			if ( !(((((_la - 146)) & ~0x3f) == 0 && ((1L << (_la - 146)) & ((1L << (PLUS - 146)) | (1L << (MINUS - 146)) | (1L << (ASTERISK - 146)) | (1L << (SLASH - 146)) | (1L << (PERCENT - 146)) | (1L << (DIV - 146)) | (1L << (TILDE - 146)) | (1L << (AMPERSAND - 146)) | (1L << (PIPE - 146)) | (1L << (CONCAT_PIPE - 146)) | (1L << (HAT - 146)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateOperatorContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(NewSqlBaseParser.OR, 0); }
		public TerminalNode AND() { return getToken(NewSqlBaseParser.AND, 0); }
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public PredicateOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPredicateOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPredicateOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPredicateOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateOperatorContext predicateOperator() throws RecognitionException {
		PredicateOperatorContext _localctx = new PredicateOperatorContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_predicateOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2250);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanValueContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(NewSqlBaseParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(NewSqlBaseParser.FALSE, 0); }
		public BooleanValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterBooleanValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitBooleanValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitBooleanValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanValueContext booleanValue() throws RecognitionException {
		BooleanValueContext _localctx = new BooleanValueContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_booleanValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2252);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntervalContext extends ParserRuleContext {
		public TerminalNode INTERVAL() { return getToken(NewSqlBaseParser.INTERVAL, 0); }
		public List<IntervalFieldContext> intervalField() {
			return getRuleContexts(IntervalFieldContext.class);
		}
		public IntervalFieldContext intervalField(int i) {
			return getRuleContext(IntervalFieldContext.class,i);
		}
		public IntervalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterInterval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitInterval(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitInterval(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntervalContext interval() throws RecognitionException {
		IntervalContext _localctx = new IntervalContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_interval);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2254);
			match(INTERVAL);
			setState(2258);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,292,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2255);
					intervalField();
					}
					} 
				}
				setState(2260);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,292,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntervalFieldContext extends ParserRuleContext {
		public IntervalValueContext value;
		public IdentifierContext unit;
		public IdentifierContext to;
		public IntervalValueContext intervalValue() {
			return getRuleContext(IntervalValueContext.class,0);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode TO() { return getToken(NewSqlBaseParser.TO, 0); }
		public IntervalFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intervalField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIntervalField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIntervalField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIntervalField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntervalFieldContext intervalField() throws RecognitionException {
		IntervalFieldContext _localctx = new IntervalFieldContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_intervalField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2261);
			((IntervalFieldContext)_localctx).value = intervalValue();
			setState(2262);
			((IntervalFieldContext)_localctx).unit = identifier();
			setState(2265);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,293,_ctx) ) {
			case 1:
				{
				setState(2263);
				match(TO);
				setState(2264);
				((IntervalFieldContext)_localctx).to = identifier();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntervalValueContext extends ParserRuleContext {
		public TerminalNode INTEGER_VALUE() { return getToken(NewSqlBaseParser.INTEGER_VALUE, 0); }
		public TerminalNode DECIMAL_VALUE() { return getToken(NewSqlBaseParser.DECIMAL_VALUE, 0); }
		public TerminalNode PLUS() { return getToken(NewSqlBaseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public IntervalValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intervalValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIntervalValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIntervalValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIntervalValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntervalValueContext intervalValue() throws RecognitionException {
		IntervalValueContext _localctx = new IntervalValueContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_intervalValue);
		int _la;
		try {
			setState(2272);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case INTEGER_VALUE:
			case DECIMAL_VALUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(2268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PLUS || _la==MINUS) {
					{
					setState(2267);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2270);
				_la = _input.LA(1);
				if ( !(_la==INTEGER_VALUE || _la==DECIMAL_VALUE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(2271);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColPositionContext extends ParserRuleContext {
		public TerminalNode FIRST() { return getToken(NewSqlBaseParser.FIRST, 0); }
		public TerminalNode AFTER() { return getToken(NewSqlBaseParser.AFTER, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ColPositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colPosition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterColPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitColPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitColPosition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColPositionContext colPosition() throws RecognitionException {
		ColPositionContext _localctx = new ColPositionContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_colPosition);
		try {
			setState(2277);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FIRST:
				enterOuterAlt(_localctx, 1);
				{
				setState(2274);
				match(FIRST);
				}
				break;
			case AFTER:
				enterOuterAlt(_localctx, 2);
				{
				setState(2275);
				match(AFTER);
				setState(2276);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataTypeContext extends ParserRuleContext {
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
	 
		public DataTypeContext() { }
		public void copyFrom(DataTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ComplexDataTypeContext extends DataTypeContext {
		public Token complex;
		public TerminalNode LT() { return getToken(NewSqlBaseParser.LT, 0); }
		public List<DataTypeContext> dataType() {
			return getRuleContexts(DataTypeContext.class);
		}
		public DataTypeContext dataType(int i) {
			return getRuleContext(DataTypeContext.class,i);
		}
		public TerminalNode GT() { return getToken(NewSqlBaseParser.GT, 0); }
		public TerminalNode ARRAY() { return getToken(NewSqlBaseParser.ARRAY, 0); }
		public TerminalNode MAP() { return getToken(NewSqlBaseParser.MAP, 0); }
		public TerminalNode STRUCT() { return getToken(NewSqlBaseParser.STRUCT, 0); }
		public TerminalNode NEQ() { return getToken(NewSqlBaseParser.NEQ, 0); }
		public ComplexColTypeListContext complexColTypeList() {
			return getRuleContext(ComplexColTypeListContext.class,0);
		}
		public ComplexDataTypeContext(DataTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterComplexDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitComplexDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitComplexDataType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrimitiveDataTypeContext extends DataTypeContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<TerminalNode> INTEGER_VALUE() { return getTokens(NewSqlBaseParser.INTEGER_VALUE); }
		public TerminalNode INTEGER_VALUE(int i) {
			return getToken(NewSqlBaseParser.INTEGER_VALUE, i);
		}
		public PrimitiveDataTypeContext(DataTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterPrimitiveDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitPrimitiveDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitPrimitiveDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_dataType);
		int _la;
		try {
			setState(2313);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,301,_ctx) ) {
			case 1:
				_localctx = new ComplexDataTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2279);
				((ComplexDataTypeContext)_localctx).complex = match(ARRAY);
				setState(2280);
				match(LT);
				setState(2281);
				dataType();
				setState(2282);
				match(GT);
				}
				break;
			case 2:
				_localctx = new ComplexDataTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2284);
				((ComplexDataTypeContext)_localctx).complex = match(MAP);
				setState(2285);
				match(LT);
				setState(2286);
				dataType();
				setState(2287);
				match(T__2);
				setState(2288);
				dataType();
				setState(2289);
				match(GT);
				}
				break;
			case 3:
				_localctx = new ComplexDataTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2291);
				((ComplexDataTypeContext)_localctx).complex = match(STRUCT);
				setState(2298);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LT:
					{
					setState(2292);
					match(LT);
					setState(2294);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (SELECT - 11)) | (1L << (FROM - 11)) | (1L << (ADD - 11)) | (1L << (AS - 11)) | (1L << (ALL - 11)) | (1L << (ANY - 11)) | (1L << (DISTINCT - 11)) | (1L << (WHERE - 11)) | (1L << (GROUP - 11)) | (1L << (BY - 11)) | (1L << (GROUPING - 11)) | (1L << (SETS - 11)) | (1L << (CUBE - 11)) | (1L << (ROLLUP - 11)) | (1L << (ORDER - 11)) | (1L << (HAVING - 11)) | (1L << (LIMIT - 11)) | (1L << (AT - 11)) | (1L << (OR - 11)) | (1L << (AND - 11)) | (1L << (IN - 11)) | (1L << (NOT - 11)) | (1L << (NO - 11)) | (1L << (EXISTS - 11)) | (1L << (BETWEEN - 11)) | (1L << (LIKE - 11)) | (1L << (RLIKE - 11)) | (1L << (IS - 11)) | (1L << (NULL - 11)) | (1L << (TRUE - 11)) | (1L << (FALSE - 11)) | (1L << (NULLS - 11)) | (1L << (ASC - 11)) | (1L << (DESC - 11)) | (1L << (FOR - 11)) | (1L << (INTERVAL - 11)) | (1L << (CASE - 11)) | (1L << (WHEN - 11)) | (1L << (THEN - 11)) | (1L << (ELSE - 11)) | (1L << (END - 11)) | (1L << (JOIN - 11)) | (1L << (CROSS - 11)) | (1L << (OUTER - 11)) | (1L << (INNER - 11)) | (1L << (LEFT - 11)) | (1L << (SEMI - 11)) | (1L << (RIGHT - 11)) | (1L << (FULL - 11)) | (1L << (NATURAL - 11)) | (1L << (ON - 11)) | (1L << (PIVOT - 11)) | (1L << (LATERAL - 11)) | (1L << (WINDOW - 11)) | (1L << (OVER - 11)) | (1L << (PARTITION - 11)) | (1L << (RANGE - 11)) | (1L << (ROWS - 11)) | (1L << (UNBOUNDED - 11)) | (1L << (PRECEDING - 11)) | (1L << (FOLLOWING - 11)) | (1L << (CURRENT - 11)) | (1L << (FIRST - 11)) | (1L << (AFTER - 11)))) != 0) || ((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & ((1L << (LAST - 75)) | (1L << (ROW - 75)) | (1L << (WITH - 75)) | (1L << (VALUES - 75)) | (1L << (CREATE - 75)) | (1L << (TABLE - 75)) | (1L << (DIRECTORY - 75)) | (1L << (VIEW - 75)) | (1L << (REPLACE - 75)) | (1L << (INSERT - 75)) | (1L << (DELETE - 75)) | (1L << (INTO - 75)) | (1L << (DESCRIBE - 75)) | (1L << (EXPLAIN - 75)) | (1L << (FORMAT - 75)) | (1L << (LOGICAL - 75)) | (1L << (CODEGEN - 75)) | (1L << (COST - 75)) | (1L << (CAST - 75)) | (1L << (SHOW - 75)) | (1L << (TABLES - 75)) | (1L << (COLUMNS - 75)) | (1L << (COLUMN - 75)) | (1L << (USE - 75)) | (1L << (PARTITIONS - 75)) | (1L << (FUNCTIONS - 75)) | (1L << (DROP - 75)) | (1L << (UNION - 75)) | (1L << (EXCEPT - 75)) | (1L << (SETMINUS - 75)) | (1L << (INTERSECT - 75)) | (1L << (TO - 75)) | (1L << (TABLESAMPLE - 75)) | (1L << (STRATIFY - 75)) | (1L << (ALTER - 75)) | (1L << (RENAME - 75)) | (1L << (ARRAY - 75)) | (1L << (MAP - 75)) | (1L << (STRUCT - 75)) | (1L << (COMMENT - 75)) | (1L << (SET - 75)) | (1L << (RESET - 75)) | (1L << (DATA - 75)) | (1L << (START - 75)) | (1L << (TRANSACTION - 75)) | (1L << (COMMIT - 75)) | (1L << (ROLLBACK - 75)) | (1L << (MACRO - 75)) | (1L << (IGNORE - 75)) | (1L << (BOTH - 75)) | (1L << (LEADING - 75)) | (1L << (TRAILING - 75)) | (1L << (IF - 75)) | (1L << (POSITION - 75)) | (1L << (EXTRACT - 75)))) != 0) || ((((_la - 151)) & ~0x3f) == 0 && ((1L << (_la - 151)) & ((1L << (DIV - 151)) | (1L << (PERCENTLIT - 151)) | (1L << (BUCKET - 151)) | (1L << (OUT - 151)) | (1L << (OF - 151)) | (1L << (SORT - 151)) | (1L << (CLUSTER - 151)) | (1L << (DISTRIBUTE - 151)) | (1L << (OVERWRITE - 151)) | (1L << (TRANSFORM - 151)) | (1L << (REDUCE - 151)) | (1L << (SERDE - 151)) | (1L << (SERDEPROPERTIES - 151)) | (1L << (RECORDREADER - 151)) | (1L << (RECORDWRITER - 151)) | (1L << (DELIMITED - 151)) | (1L << (FIELDS - 151)) | (1L << (TERMINATED - 151)) | (1L << (COLLECTION - 151)) | (1L << (ITEMS - 151)) | (1L << (KEYS - 151)) | (1L << (ESCAPED - 151)) | (1L << (LINES - 151)) | (1L << (SEPARATED - 151)) | (1L << (FUNCTION - 151)) | (1L << (EXTENDED - 151)) | (1L << (REFRESH - 151)) | (1L << (CLEAR - 151)) | (1L << (CACHE - 151)) | (1L << (UNCACHE - 151)) | (1L << (LAZY - 151)) | (1L << (FORMATTED - 151)) | (1L << (GLOBAL - 151)) | (1L << (TEMPORARY - 151)) | (1L << (OPTIONS - 151)) | (1L << (UNSET - 151)) | (1L << (TBLPROPERTIES - 151)) | (1L << (DBPROPERTIES - 151)) | (1L << (BUCKETS - 151)) | (1L << (SKEWED - 151)) | (1L << (STORED - 151)) | (1L << (DIRECTORIES - 151)) | (1L << (LOCATION - 151)) | (1L << (EXCHANGE - 151)) | (1L << (ARCHIVE - 151)) | (1L << (UNARCHIVE - 151)) | (1L << (FILEFORMAT - 151)) | (1L << (TOUCH - 151)) | (1L << (COMPACT - 151)) | (1L << (CONCATENATE - 151)) | (1L << (CHANGE - 151)) | (1L << (CASCADE - 151)) | (1L << (RESTRICT - 151)) | (1L << (CLUSTERED - 151)) | (1L << (SORTED - 151)) | (1L << (PURGE - 151)) | (1L << (INPUTFORMAT - 151)) | (1L << (OUTPUTFORMAT - 151)))) != 0) || ((((_la - 215)) & ~0x3f) == 0 && ((1L << (_la - 215)) & ((1L << (DATABASE - 215)) | (1L << (DATABASES - 215)) | (1L << (DFS - 215)) | (1L << (TRUNCATE - 215)) | (1L << (ANALYZE - 215)) | (1L << (COMPUTE - 215)) | (1L << (LIST - 215)) | (1L << (STATISTICS - 215)) | (1L << (PARTITIONED - 215)) | (1L << (EXTERNAL - 215)) | (1L << (DEFINED - 215)) | (1L << (REVOKE - 215)) | (1L << (GRANT - 215)) | (1L << (LOCK - 215)) | (1L << (UNLOCK - 215)) | (1L << (MSCK - 215)) | (1L << (REPAIR - 215)) | (1L << (RECOVER - 215)) | (1L << (EXPORT - 215)) | (1L << (IMPORT - 215)) | (1L << (LOAD - 215)) | (1L << (ROLE - 215)) | (1L << (ROLES - 215)) | (1L << (COMPACTIONS - 215)) | (1L << (PRINCIPALS - 215)) | (1L << (TRANSACTIONS - 215)) | (1L << (INDEX - 215)) | (1L << (INDEXES - 215)) | (1L << (LOCKS - 215)) | (1L << (OPTION - 215)) | (1L << (ANTI - 215)) | (1L << (LOCAL - 215)) | (1L << (INPATH - 215)) | (1L << (IDENTIFIER - 215)) | (1L << (BACKQUOTED_IDENTIFIER - 215)))) != 0)) {
						{
						setState(2293);
						complexColTypeList();
						}
					}

					setState(2296);
					match(GT);
					}
					break;
				case NEQ:
					{
					setState(2297);
					match(NEQ);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 4:
				_localctx = new PrimitiveDataTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2300);
				identifier();
				setState(2311);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,300,_ctx) ) {
				case 1:
					{
					setState(2301);
					match(T__0);
					setState(2302);
					match(INTEGER_VALUE);
					setState(2307);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(2303);
						match(T__2);
						setState(2304);
						match(INTEGER_VALUE);
						}
						}
						setState(2309);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(2310);
					match(T__1);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColTypeListContext extends ParserRuleContext {
		public List<ColTypeContext> colType() {
			return getRuleContexts(ColTypeContext.class);
		}
		public ColTypeContext colType(int i) {
			return getRuleContext(ColTypeContext.class,i);
		}
		public ColTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colTypeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterColTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitColTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitColTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColTypeListContext colTypeList() throws RecognitionException {
		ColTypeListContext _localctx = new ColTypeListContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_colTypeList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2315);
			colType();
			setState(2320);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,302,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2316);
					match(T__2);
					setState(2317);
					colType();
					}
					} 
				}
				setState(2322);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,302,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColTypeContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public TerminalNode COMMENT() { return getToken(NewSqlBaseParser.COMMENT, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public ColTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterColType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitColType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitColType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColTypeContext colType() throws RecognitionException {
		ColTypeContext _localctx = new ColTypeContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_colType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2323);
			identifier();
			setState(2324);
			dataType();
			setState(2327);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,303,_ctx) ) {
			case 1:
				{
				setState(2325);
				match(COMMENT);
				setState(2326);
				match(STRING);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComplexColTypeListContext extends ParserRuleContext {
		public List<ComplexColTypeContext> complexColType() {
			return getRuleContexts(ComplexColTypeContext.class);
		}
		public ComplexColTypeContext complexColType(int i) {
			return getRuleContext(ComplexColTypeContext.class,i);
		}
		public ComplexColTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_complexColTypeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterComplexColTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitComplexColTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitComplexColTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComplexColTypeListContext complexColTypeList() throws RecognitionException {
		ComplexColTypeListContext _localctx = new ComplexColTypeListContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_complexColTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2329);
			complexColType();
			setState(2334);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(2330);
				match(T__2);
				setState(2331);
				complexColType();
				}
				}
				setState(2336);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComplexColTypeContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public TerminalNode COMMENT() { return getToken(NewSqlBaseParser.COMMENT, 0); }
		public TerminalNode STRING() { return getToken(NewSqlBaseParser.STRING, 0); }
		public ComplexColTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_complexColType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterComplexColType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitComplexColType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitComplexColType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComplexColTypeContext complexColType() throws RecognitionException {
		ComplexColTypeContext _localctx = new ComplexColTypeContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_complexColType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2337);
			identifier();
			setState(2338);
			match(T__9);
			setState(2339);
			dataType();
			setState(2342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(2340);
				match(COMMENT);
				setState(2341);
				match(STRING);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhenClauseContext extends ParserRuleContext {
		public ExpressionContext condition;
		public ExpressionContext result;
		public TerminalNode WHEN() { return getToken(NewSqlBaseParser.WHEN, 0); }
		public TerminalNode THEN() { return getToken(NewSqlBaseParser.THEN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public WhenClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterWhenClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitWhenClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitWhenClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenClauseContext whenClause() throws RecognitionException {
		WhenClauseContext _localctx = new WhenClauseContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_whenClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2344);
			match(WHEN);
			setState(2345);
			((WhenClauseContext)_localctx).condition = expression();
			setState(2346);
			match(THEN);
			setState(2347);
			((WhenClauseContext)_localctx).result = expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WindowsContext extends ParserRuleContext {
		public TerminalNode WINDOW() { return getToken(NewSqlBaseParser.WINDOW, 0); }
		public List<NamedWindowContext> namedWindow() {
			return getRuleContexts(NamedWindowContext.class);
		}
		public NamedWindowContext namedWindow(int i) {
			return getRuleContext(NamedWindowContext.class,i);
		}
		public WindowsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windows; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterWindows(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitWindows(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitWindows(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowsContext windows() throws RecognitionException {
		WindowsContext _localctx = new WindowsContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_windows);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2349);
			match(WINDOW);
			setState(2350);
			namedWindow();
			setState(2355);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,306,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2351);
					match(T__2);
					setState(2352);
					namedWindow();
					}
					} 
				}
				setState(2357);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,306,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedWindowContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public WindowSpecContext windowSpec() {
			return getRuleContext(WindowSpecContext.class,0);
		}
		public NamedWindowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedWindow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNamedWindow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNamedWindow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNamedWindow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedWindowContext namedWindow() throws RecognitionException {
		NamedWindowContext _localctx = new NamedWindowContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_namedWindow);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2358);
			identifier();
			setState(2359);
			match(AS);
			setState(2360);
			windowSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WindowSpecContext extends ParserRuleContext {
		public WindowSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowSpec; }
	 
		public WindowSpecContext() { }
		public void copyFrom(WindowSpecContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WindowRefContext extends WindowSpecContext {
		public IdentifierContext name;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public WindowRefContext(WindowSpecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterWindowRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitWindowRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitWindowRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WindowDefContext extends WindowSpecContext {
		public ExpressionContext expression;
		public List<ExpressionContext> partition = new ArrayList<ExpressionContext>();
		public TerminalNode CLUSTER() { return getToken(NewSqlBaseParser.CLUSTER, 0); }
		public List<TerminalNode> BY() { return getTokens(NewSqlBaseParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(NewSqlBaseParser.BY, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public WindowFrameContext windowFrame() {
			return getRuleContext(WindowFrameContext.class,0);
		}
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public TerminalNode PARTITION() { return getToken(NewSqlBaseParser.PARTITION, 0); }
		public TerminalNode DISTRIBUTE() { return getToken(NewSqlBaseParser.DISTRIBUTE, 0); }
		public TerminalNode ORDER() { return getToken(NewSqlBaseParser.ORDER, 0); }
		public TerminalNode SORT() { return getToken(NewSqlBaseParser.SORT, 0); }
		public WindowDefContext(WindowSpecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterWindowDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitWindowDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitWindowDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowSpecContext windowSpec() throws RecognitionException {
		WindowSpecContext _localctx = new WindowSpecContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_windowSpec);
		int _la;
		try {
			setState(2408);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,314,_ctx) ) {
			case 1:
				_localctx = new WindowRefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2362);
				((WindowRefContext)_localctx).name = identifier();
				}
				break;
			case 2:
				_localctx = new WindowRefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2363);
				match(T__0);
				setState(2364);
				((WindowRefContext)_localctx).name = identifier();
				setState(2365);
				match(T__1);
				}
				break;
			case 3:
				_localctx = new WindowDefContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2367);
				match(T__0);
				setState(2402);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CLUSTER:
					{
					setState(2368);
					match(CLUSTER);
					setState(2369);
					match(BY);
					setState(2370);
					((WindowDefContext)_localctx).expression = expression();
					((WindowDefContext)_localctx).partition.add(((WindowDefContext)_localctx).expression);
					setState(2375);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(2371);
						match(T__2);
						setState(2372);
						((WindowDefContext)_localctx).expression = expression();
						((WindowDefContext)_localctx).partition.add(((WindowDefContext)_localctx).expression);
						}
						}
						setState(2377);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				case T__1:
				case ORDER:
				case PARTITION:
				case RANGE:
				case ROWS:
				case SORT:
				case DISTRIBUTE:
					{
					setState(2388);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==PARTITION || _la==DISTRIBUTE) {
						{
						setState(2378);
						_la = _input.LA(1);
						if ( !(_la==PARTITION || _la==DISTRIBUTE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2379);
						match(BY);
						setState(2380);
						((WindowDefContext)_localctx).expression = expression();
						((WindowDefContext)_localctx).partition.add(((WindowDefContext)_localctx).expression);
						setState(2385);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==T__2) {
							{
							{
							setState(2381);
							match(T__2);
							setState(2382);
							((WindowDefContext)_localctx).expression = expression();
							((WindowDefContext)_localctx).partition.add(((WindowDefContext)_localctx).expression);
							}
							}
							setState(2387);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						}
					}

					setState(2400);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ORDER || _la==SORT) {
						{
						setState(2390);
						_la = _input.LA(1);
						if ( !(_la==ORDER || _la==SORT) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2391);
						match(BY);
						setState(2392);
						sortItem();
						setState(2397);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==T__2) {
							{
							{
							setState(2393);
							match(T__2);
							setState(2394);
							sortItem();
							}
							}
							setState(2399);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						}
					}

					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(2405);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RANGE || _la==ROWS) {
					{
					setState(2404);
					windowFrame();
					}
				}

				setState(2407);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WindowFrameContext extends ParserRuleContext {
		public Token frameType;
		public FrameBoundContext start;
		public FrameBoundContext end;
		public TerminalNode RANGE() { return getToken(NewSqlBaseParser.RANGE, 0); }
		public List<FrameBoundContext> frameBound() {
			return getRuleContexts(FrameBoundContext.class);
		}
		public FrameBoundContext frameBound(int i) {
			return getRuleContext(FrameBoundContext.class,i);
		}
		public TerminalNode ROWS() { return getToken(NewSqlBaseParser.ROWS, 0); }
		public TerminalNode BETWEEN() { return getToken(NewSqlBaseParser.BETWEEN, 0); }
		public TerminalNode AND() { return getToken(NewSqlBaseParser.AND, 0); }
		public WindowFrameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowFrame; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterWindowFrame(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitWindowFrame(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitWindowFrame(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowFrameContext windowFrame() throws RecognitionException {
		WindowFrameContext _localctx = new WindowFrameContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_windowFrame);
		try {
			setState(2426);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,315,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2410);
				((WindowFrameContext)_localctx).frameType = match(RANGE);
				setState(2411);
				((WindowFrameContext)_localctx).start = frameBound();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2412);
				((WindowFrameContext)_localctx).frameType = match(ROWS);
				setState(2413);
				((WindowFrameContext)_localctx).start = frameBound();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2414);
				((WindowFrameContext)_localctx).frameType = match(RANGE);
				setState(2415);
				match(BETWEEN);
				setState(2416);
				((WindowFrameContext)_localctx).start = frameBound();
				setState(2417);
				match(AND);
				setState(2418);
				((WindowFrameContext)_localctx).end = frameBound();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2420);
				((WindowFrameContext)_localctx).frameType = match(ROWS);
				setState(2421);
				match(BETWEEN);
				setState(2422);
				((WindowFrameContext)_localctx).start = frameBound();
				setState(2423);
				match(AND);
				setState(2424);
				((WindowFrameContext)_localctx).end = frameBound();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FrameBoundContext extends ParserRuleContext {
		public Token boundType;
		public TerminalNode UNBOUNDED() { return getToken(NewSqlBaseParser.UNBOUNDED, 0); }
		public TerminalNode PRECEDING() { return getToken(NewSqlBaseParser.PRECEDING, 0); }
		public TerminalNode FOLLOWING() { return getToken(NewSqlBaseParser.FOLLOWING, 0); }
		public TerminalNode ROW() { return getToken(NewSqlBaseParser.ROW, 0); }
		public TerminalNode CURRENT() { return getToken(NewSqlBaseParser.CURRENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FrameBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_frameBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterFrameBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitFrameBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitFrameBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FrameBoundContext frameBound() throws RecognitionException {
		FrameBoundContext _localctx = new FrameBoundContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_frameBound);
		int _la;
		try {
			setState(2435);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,316,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2428);
				match(UNBOUNDED);
				setState(2429);
				((FrameBoundContext)_localctx).boundType = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PRECEDING || _la==FOLLOWING) ) {
					((FrameBoundContext)_localctx).boundType = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2430);
				((FrameBoundContext)_localctx).boundType = match(CURRENT);
				setState(2431);
				match(ROW);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2432);
				expression();
				setState(2433);
				((FrameBoundContext)_localctx).boundType = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PRECEDING || _la==FOLLOWING) ) {
					((FrameBoundContext)_localctx).boundType = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2437);
			identifier();
			setState(2442);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,317,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2438);
					match(T__3);
					setState(2439);
					identifier();
					}
					} 
				}
				setState(2444);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,317,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public StrictIdentifierContext strictIdentifier() {
			return getRuleContext(StrictIdentifierContext.class,0);
		}
		public TerminalNode ANTI() { return getToken(NewSqlBaseParser.ANTI, 0); }
		public TerminalNode FULL() { return getToken(NewSqlBaseParser.FULL, 0); }
		public TerminalNode INNER() { return getToken(NewSqlBaseParser.INNER, 0); }
		public TerminalNode LEFT() { return getToken(NewSqlBaseParser.LEFT, 0); }
		public TerminalNode SEMI() { return getToken(NewSqlBaseParser.SEMI, 0); }
		public TerminalNode RIGHT() { return getToken(NewSqlBaseParser.RIGHT, 0); }
		public TerminalNode NATURAL() { return getToken(NewSqlBaseParser.NATURAL, 0); }
		public TerminalNode JOIN() { return getToken(NewSqlBaseParser.JOIN, 0); }
		public TerminalNode CROSS() { return getToken(NewSqlBaseParser.CROSS, 0); }
		public TerminalNode ON() { return getToken(NewSqlBaseParser.ON, 0); }
		public TerminalNode UNION() { return getToken(NewSqlBaseParser.UNION, 0); }
		public TerminalNode INTERSECT() { return getToken(NewSqlBaseParser.INTERSECT, 0); }
		public TerminalNode EXCEPT() { return getToken(NewSqlBaseParser.EXCEPT, 0); }
		public TerminalNode SETMINUS() { return getToken(NewSqlBaseParser.SETMINUS, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_identifier);
		try {
			setState(2460);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
			case FROM:
			case ADD:
			case AS:
			case ALL:
			case ANY:
			case DISTINCT:
			case WHERE:
			case GROUP:
			case BY:
			case GROUPING:
			case SETS:
			case CUBE:
			case ROLLUP:
			case ORDER:
			case HAVING:
			case LIMIT:
			case AT:
			case OR:
			case AND:
			case IN:
			case NOT:
			case NO:
			case EXISTS:
			case BETWEEN:
			case LIKE:
			case RLIKE:
			case IS:
			case NULL:
			case TRUE:
			case FALSE:
			case NULLS:
			case ASC:
			case DESC:
			case FOR:
			case INTERVAL:
			case CASE:
			case WHEN:
			case THEN:
			case ELSE:
			case END:
			case OUTER:
			case PIVOT:
			case LATERAL:
			case WINDOW:
			case OVER:
			case PARTITION:
			case RANGE:
			case ROWS:
			case UNBOUNDED:
			case PRECEDING:
			case FOLLOWING:
			case CURRENT:
			case FIRST:
			case AFTER:
			case LAST:
			case ROW:
			case WITH:
			case VALUES:
			case CREATE:
			case TABLE:
			case DIRECTORY:
			case VIEW:
			case REPLACE:
			case INSERT:
			case DELETE:
			case INTO:
			case DESCRIBE:
			case EXPLAIN:
			case FORMAT:
			case LOGICAL:
			case CODEGEN:
			case COST:
			case CAST:
			case SHOW:
			case TABLES:
			case COLUMNS:
			case COLUMN:
			case USE:
			case PARTITIONS:
			case FUNCTIONS:
			case DROP:
			case TO:
			case TABLESAMPLE:
			case STRATIFY:
			case ALTER:
			case RENAME:
			case ARRAY:
			case MAP:
			case STRUCT:
			case COMMENT:
			case SET:
			case RESET:
			case DATA:
			case START:
			case TRANSACTION:
			case COMMIT:
			case ROLLBACK:
			case MACRO:
			case IGNORE:
			case BOTH:
			case LEADING:
			case TRAILING:
			case IF:
			case POSITION:
			case EXTRACT:
			case DIV:
			case PERCENTLIT:
			case BUCKET:
			case OUT:
			case OF:
			case SORT:
			case CLUSTER:
			case DISTRIBUTE:
			case OVERWRITE:
			case TRANSFORM:
			case REDUCE:
			case SERDE:
			case SERDEPROPERTIES:
			case RECORDREADER:
			case RECORDWRITER:
			case DELIMITED:
			case FIELDS:
			case TERMINATED:
			case COLLECTION:
			case ITEMS:
			case KEYS:
			case ESCAPED:
			case LINES:
			case SEPARATED:
			case FUNCTION:
			case EXTENDED:
			case REFRESH:
			case CLEAR:
			case CACHE:
			case UNCACHE:
			case LAZY:
			case FORMATTED:
			case GLOBAL:
			case TEMPORARY:
			case OPTIONS:
			case UNSET:
			case TBLPROPERTIES:
			case DBPROPERTIES:
			case BUCKETS:
			case SKEWED:
			case STORED:
			case DIRECTORIES:
			case LOCATION:
			case EXCHANGE:
			case ARCHIVE:
			case UNARCHIVE:
			case FILEFORMAT:
			case TOUCH:
			case COMPACT:
			case CONCATENATE:
			case CHANGE:
			case CASCADE:
			case RESTRICT:
			case CLUSTERED:
			case SORTED:
			case PURGE:
			case INPUTFORMAT:
			case OUTPUTFORMAT:
			case DATABASE:
			case DATABASES:
			case DFS:
			case TRUNCATE:
			case ANALYZE:
			case COMPUTE:
			case LIST:
			case STATISTICS:
			case PARTITIONED:
			case EXTERNAL:
			case DEFINED:
			case REVOKE:
			case GRANT:
			case LOCK:
			case UNLOCK:
			case MSCK:
			case REPAIR:
			case RECOVER:
			case EXPORT:
			case IMPORT:
			case LOAD:
			case ROLE:
			case ROLES:
			case COMPACTIONS:
			case PRINCIPALS:
			case TRANSACTIONS:
			case INDEX:
			case INDEXES:
			case LOCKS:
			case OPTION:
			case LOCAL:
			case INPATH:
			case IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(2445);
				strictIdentifier();
				}
				break;
			case ANTI:
				enterOuterAlt(_localctx, 2);
				{
				setState(2446);
				match(ANTI);
				}
				break;
			case FULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(2447);
				match(FULL);
				}
				break;
			case INNER:
				enterOuterAlt(_localctx, 4);
				{
				setState(2448);
				match(INNER);
				}
				break;
			case LEFT:
				enterOuterAlt(_localctx, 5);
				{
				setState(2449);
				match(LEFT);
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 6);
				{
				setState(2450);
				match(SEMI);
				}
				break;
			case RIGHT:
				enterOuterAlt(_localctx, 7);
				{
				setState(2451);
				match(RIGHT);
				}
				break;
			case NATURAL:
				enterOuterAlt(_localctx, 8);
				{
				setState(2452);
				match(NATURAL);
				}
				break;
			case JOIN:
				enterOuterAlt(_localctx, 9);
				{
				setState(2453);
				match(JOIN);
				}
				break;
			case CROSS:
				enterOuterAlt(_localctx, 10);
				{
				setState(2454);
				match(CROSS);
				}
				break;
			case ON:
				enterOuterAlt(_localctx, 11);
				{
				setState(2455);
				match(ON);
				}
				break;
			case UNION:
				enterOuterAlt(_localctx, 12);
				{
				setState(2456);
				match(UNION);
				}
				break;
			case INTERSECT:
				enterOuterAlt(_localctx, 13);
				{
				setState(2457);
				match(INTERSECT);
				}
				break;
			case EXCEPT:
				enterOuterAlt(_localctx, 14);
				{
				setState(2458);
				match(EXCEPT);
				}
				break;
			case SETMINUS:
				enterOuterAlt(_localctx, 15);
				{
				setState(2459);
				match(SETMINUS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StrictIdentifierContext extends ParserRuleContext {
		public StrictIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strictIdentifier; }
	 
		public StrictIdentifierContext() { }
		public void copyFrom(StrictIdentifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class QuotedIdentifierAlternativeContext extends StrictIdentifierContext {
		public QuotedIdentifierContext quotedIdentifier() {
			return getRuleContext(QuotedIdentifierContext.class,0);
		}
		public QuotedIdentifierAlternativeContext(StrictIdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQuotedIdentifierAlternative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQuotedIdentifierAlternative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQuotedIdentifierAlternative(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnquotedIdentifierContext extends StrictIdentifierContext {
		public TerminalNode IDENTIFIER() { return getToken(NewSqlBaseParser.IDENTIFIER, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public UnquotedIdentifierContext(StrictIdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterUnquotedIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitUnquotedIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitUnquotedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrictIdentifierContext strictIdentifier() throws RecognitionException {
		StrictIdentifierContext _localctx = new StrictIdentifierContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_strictIdentifier);
		try {
			setState(2465);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				_localctx = new UnquotedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2462);
				match(IDENTIFIER);
				}
				break;
			case BACKQUOTED_IDENTIFIER:
				_localctx = new QuotedIdentifierAlternativeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2463);
				quotedIdentifier();
				}
				break;
			case SELECT:
			case FROM:
			case ADD:
			case AS:
			case ALL:
			case ANY:
			case DISTINCT:
			case WHERE:
			case GROUP:
			case BY:
			case GROUPING:
			case SETS:
			case CUBE:
			case ROLLUP:
			case ORDER:
			case HAVING:
			case LIMIT:
			case AT:
			case OR:
			case AND:
			case IN:
			case NOT:
			case NO:
			case EXISTS:
			case BETWEEN:
			case LIKE:
			case RLIKE:
			case IS:
			case NULL:
			case TRUE:
			case FALSE:
			case NULLS:
			case ASC:
			case DESC:
			case FOR:
			case INTERVAL:
			case CASE:
			case WHEN:
			case THEN:
			case ELSE:
			case END:
			case OUTER:
			case PIVOT:
			case LATERAL:
			case WINDOW:
			case OVER:
			case PARTITION:
			case RANGE:
			case ROWS:
			case UNBOUNDED:
			case PRECEDING:
			case FOLLOWING:
			case CURRENT:
			case FIRST:
			case AFTER:
			case LAST:
			case ROW:
			case WITH:
			case VALUES:
			case CREATE:
			case TABLE:
			case DIRECTORY:
			case VIEW:
			case REPLACE:
			case INSERT:
			case DELETE:
			case INTO:
			case DESCRIBE:
			case EXPLAIN:
			case FORMAT:
			case LOGICAL:
			case CODEGEN:
			case COST:
			case CAST:
			case SHOW:
			case TABLES:
			case COLUMNS:
			case COLUMN:
			case USE:
			case PARTITIONS:
			case FUNCTIONS:
			case DROP:
			case TO:
			case TABLESAMPLE:
			case STRATIFY:
			case ALTER:
			case RENAME:
			case ARRAY:
			case MAP:
			case STRUCT:
			case COMMENT:
			case SET:
			case RESET:
			case DATA:
			case START:
			case TRANSACTION:
			case COMMIT:
			case ROLLBACK:
			case MACRO:
			case IGNORE:
			case BOTH:
			case LEADING:
			case TRAILING:
			case IF:
			case POSITION:
			case EXTRACT:
			case DIV:
			case PERCENTLIT:
			case BUCKET:
			case OUT:
			case OF:
			case SORT:
			case CLUSTER:
			case DISTRIBUTE:
			case OVERWRITE:
			case TRANSFORM:
			case REDUCE:
			case SERDE:
			case SERDEPROPERTIES:
			case RECORDREADER:
			case RECORDWRITER:
			case DELIMITED:
			case FIELDS:
			case TERMINATED:
			case COLLECTION:
			case ITEMS:
			case KEYS:
			case ESCAPED:
			case LINES:
			case SEPARATED:
			case FUNCTION:
			case EXTENDED:
			case REFRESH:
			case CLEAR:
			case CACHE:
			case UNCACHE:
			case LAZY:
			case FORMATTED:
			case GLOBAL:
			case TEMPORARY:
			case OPTIONS:
			case UNSET:
			case TBLPROPERTIES:
			case DBPROPERTIES:
			case BUCKETS:
			case SKEWED:
			case STORED:
			case DIRECTORIES:
			case LOCATION:
			case EXCHANGE:
			case ARCHIVE:
			case UNARCHIVE:
			case FILEFORMAT:
			case TOUCH:
			case COMPACT:
			case CONCATENATE:
			case CHANGE:
			case CASCADE:
			case RESTRICT:
			case CLUSTERED:
			case SORTED:
			case PURGE:
			case INPUTFORMAT:
			case OUTPUTFORMAT:
			case DATABASE:
			case DATABASES:
			case DFS:
			case TRUNCATE:
			case ANALYZE:
			case COMPUTE:
			case LIST:
			case STATISTICS:
			case PARTITIONED:
			case EXTERNAL:
			case DEFINED:
			case REVOKE:
			case GRANT:
			case LOCK:
			case UNLOCK:
			case MSCK:
			case REPAIR:
			case RECOVER:
			case EXPORT:
			case IMPORT:
			case LOAD:
			case ROLE:
			case ROLES:
			case COMPACTIONS:
			case PRINCIPALS:
			case TRANSACTIONS:
			case INDEX:
			case INDEXES:
			case LOCKS:
			case OPTION:
			case LOCAL:
			case INPATH:
				_localctx = new UnquotedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2464);
				nonReserved();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuotedIdentifierContext extends ParserRuleContext {
		public TerminalNode BACKQUOTED_IDENTIFIER() { return getToken(NewSqlBaseParser.BACKQUOTED_IDENTIFIER, 0); }
		public QuotedIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quotedIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterQuotedIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitQuotedIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitQuotedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuotedIdentifierContext quotedIdentifier() throws RecognitionException {
		QuotedIdentifierContext _localctx = new QuotedIdentifierContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_quotedIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2467);
			match(BACKQUOTED_IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
	 
		public NumberContext() { }
		public void copyFrom(NumberContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DecimalLiteralContext extends NumberContext {
		public TerminalNode DECIMAL_VALUE() { return getToken(NewSqlBaseParser.DECIMAL_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public DecimalLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDecimalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDecimalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BigIntLiteralContext extends NumberContext {
		public TerminalNode BIGINT_LITERAL() { return getToken(NewSqlBaseParser.BIGINT_LITERAL, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public BigIntLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterBigIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitBigIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitBigIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TinyIntLiteralContext extends NumberContext {
		public TerminalNode TINYINT_LITERAL() { return getToken(NewSqlBaseParser.TINYINT_LITERAL, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public TinyIntLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterTinyIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitTinyIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitTinyIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BigDecimalLiteralContext extends NumberContext {
		public TerminalNode BIGDECIMAL_LITERAL() { return getToken(NewSqlBaseParser.BIGDECIMAL_LITERAL, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public BigDecimalLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterBigDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitBigDecimalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitBigDecimalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DoubleLiteralContext extends NumberContext {
		public TerminalNode DOUBLE_LITERAL() { return getToken(NewSqlBaseParser.DOUBLE_LITERAL, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public DoubleLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterDoubleLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitDoubleLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitDoubleLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerLiteralContext extends NumberContext {
		public TerminalNode INTEGER_VALUE() { return getToken(NewSqlBaseParser.INTEGER_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public IntegerLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SmallIntLiteralContext extends NumberContext {
		public TerminalNode SMALLINT_LITERAL() { return getToken(NewSqlBaseParser.SMALLINT_LITERAL, 0); }
		public TerminalNode MINUS() { return getToken(NewSqlBaseParser.MINUS, 0); }
		public SmallIntLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterSmallIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitSmallIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitSmallIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_number);
		int _la;
		try {
			setState(2497);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,327,_ctx) ) {
			case 1:
				_localctx = new DecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2470);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2469);
					match(MINUS);
					}
				}

				setState(2472);
				match(DECIMAL_VALUE);
				}
				break;
			case 2:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2474);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2473);
					match(MINUS);
					}
				}

				setState(2476);
				match(INTEGER_VALUE);
				}
				break;
			case 3:
				_localctx = new BigIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2478);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2477);
					match(MINUS);
					}
				}

				setState(2480);
				match(BIGINT_LITERAL);
				}
				break;
			case 4:
				_localctx = new SmallIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2482);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2481);
					match(MINUS);
					}
				}

				setState(2484);
				match(SMALLINT_LITERAL);
				}
				break;
			case 5:
				_localctx = new TinyIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2486);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2485);
					match(MINUS);
					}
				}

				setState(2488);
				match(TINYINT_LITERAL);
				}
				break;
			case 6:
				_localctx = new DoubleLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(2490);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2489);
					match(MINUS);
					}
				}

				setState(2492);
				match(DOUBLE_LITERAL);
				}
				break;
			case 7:
				_localctx = new BigDecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(2494);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2493);
					match(MINUS);
					}
				}

				setState(2496);
				match(BIGDECIMAL_LITERAL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonReservedContext extends ParserRuleContext {
		public TerminalNode SHOW() { return getToken(NewSqlBaseParser.SHOW, 0); }
		public TerminalNode TABLES() { return getToken(NewSqlBaseParser.TABLES, 0); }
		public TerminalNode COLUMNS() { return getToken(NewSqlBaseParser.COLUMNS, 0); }
		public TerminalNode COLUMN() { return getToken(NewSqlBaseParser.COLUMN, 0); }
		public TerminalNode PARTITIONS() { return getToken(NewSqlBaseParser.PARTITIONS, 0); }
		public TerminalNode FUNCTIONS() { return getToken(NewSqlBaseParser.FUNCTIONS, 0); }
		public TerminalNode DATABASES() { return getToken(NewSqlBaseParser.DATABASES, 0); }
		public TerminalNode ADD() { return getToken(NewSqlBaseParser.ADD, 0); }
		public TerminalNode OVER() { return getToken(NewSqlBaseParser.OVER, 0); }
		public TerminalNode PARTITION() { return getToken(NewSqlBaseParser.PARTITION, 0); }
		public TerminalNode RANGE() { return getToken(NewSqlBaseParser.RANGE, 0); }
		public TerminalNode ROWS() { return getToken(NewSqlBaseParser.ROWS, 0); }
		public TerminalNode PRECEDING() { return getToken(NewSqlBaseParser.PRECEDING, 0); }
		public TerminalNode FOLLOWING() { return getToken(NewSqlBaseParser.FOLLOWING, 0); }
		public TerminalNode CURRENT() { return getToken(NewSqlBaseParser.CURRENT, 0); }
		public TerminalNode ROW() { return getToken(NewSqlBaseParser.ROW, 0); }
		public TerminalNode LAST() { return getToken(NewSqlBaseParser.LAST, 0); }
		public TerminalNode FIRST() { return getToken(NewSqlBaseParser.FIRST, 0); }
		public TerminalNode AFTER() { return getToken(NewSqlBaseParser.AFTER, 0); }
		public TerminalNode MAP() { return getToken(NewSqlBaseParser.MAP, 0); }
		public TerminalNode ARRAY() { return getToken(NewSqlBaseParser.ARRAY, 0); }
		public TerminalNode STRUCT() { return getToken(NewSqlBaseParser.STRUCT, 0); }
		public TerminalNode PIVOT() { return getToken(NewSqlBaseParser.PIVOT, 0); }
		public TerminalNode LATERAL() { return getToken(NewSqlBaseParser.LATERAL, 0); }
		public TerminalNode WINDOW() { return getToken(NewSqlBaseParser.WINDOW, 0); }
		public TerminalNode REDUCE() { return getToken(NewSqlBaseParser.REDUCE, 0); }
		public TerminalNode TRANSFORM() { return getToken(NewSqlBaseParser.TRANSFORM, 0); }
		public TerminalNode SERDE() { return getToken(NewSqlBaseParser.SERDE, 0); }
		public TerminalNode SERDEPROPERTIES() { return getToken(NewSqlBaseParser.SERDEPROPERTIES, 0); }
		public TerminalNode RECORDREADER() { return getToken(NewSqlBaseParser.RECORDREADER, 0); }
		public TerminalNode DELIMITED() { return getToken(NewSqlBaseParser.DELIMITED, 0); }
		public TerminalNode FIELDS() { return getToken(NewSqlBaseParser.FIELDS, 0); }
		public TerminalNode TERMINATED() { return getToken(NewSqlBaseParser.TERMINATED, 0); }
		public TerminalNode COLLECTION() { return getToken(NewSqlBaseParser.COLLECTION, 0); }
		public TerminalNode ITEMS() { return getToken(NewSqlBaseParser.ITEMS, 0); }
		public TerminalNode KEYS() { return getToken(NewSqlBaseParser.KEYS, 0); }
		public TerminalNode ESCAPED() { return getToken(NewSqlBaseParser.ESCAPED, 0); }
		public TerminalNode LINES() { return getToken(NewSqlBaseParser.LINES, 0); }
		public TerminalNode SEPARATED() { return getToken(NewSqlBaseParser.SEPARATED, 0); }
		public TerminalNode EXTENDED() { return getToken(NewSqlBaseParser.EXTENDED, 0); }
		public TerminalNode REFRESH() { return getToken(NewSqlBaseParser.REFRESH, 0); }
		public TerminalNode CLEAR() { return getToken(NewSqlBaseParser.CLEAR, 0); }
		public TerminalNode CACHE() { return getToken(NewSqlBaseParser.CACHE, 0); }
		public TerminalNode UNCACHE() { return getToken(NewSqlBaseParser.UNCACHE, 0); }
		public TerminalNode LAZY() { return getToken(NewSqlBaseParser.LAZY, 0); }
		public TerminalNode GLOBAL() { return getToken(NewSqlBaseParser.GLOBAL, 0); }
		public TerminalNode TEMPORARY() { return getToken(NewSqlBaseParser.TEMPORARY, 0); }
		public TerminalNode OPTIONS() { return getToken(NewSqlBaseParser.OPTIONS, 0); }
		public TerminalNode GROUPING() { return getToken(NewSqlBaseParser.GROUPING, 0); }
		public TerminalNode CUBE() { return getToken(NewSqlBaseParser.CUBE, 0); }
		public TerminalNode ROLLUP() { return getToken(NewSqlBaseParser.ROLLUP, 0); }
		public TerminalNode EXPLAIN() { return getToken(NewSqlBaseParser.EXPLAIN, 0); }
		public TerminalNode FORMAT() { return getToken(NewSqlBaseParser.FORMAT, 0); }
		public TerminalNode LOGICAL() { return getToken(NewSqlBaseParser.LOGICAL, 0); }
		public TerminalNode FORMATTED() { return getToken(NewSqlBaseParser.FORMATTED, 0); }
		public TerminalNode CODEGEN() { return getToken(NewSqlBaseParser.CODEGEN, 0); }
		public TerminalNode COST() { return getToken(NewSqlBaseParser.COST, 0); }
		public TerminalNode TABLESAMPLE() { return getToken(NewSqlBaseParser.TABLESAMPLE, 0); }
		public TerminalNode USE() { return getToken(NewSqlBaseParser.USE, 0); }
		public TerminalNode TO() { return getToken(NewSqlBaseParser.TO, 0); }
		public TerminalNode BUCKET() { return getToken(NewSqlBaseParser.BUCKET, 0); }
		public TerminalNode PERCENTLIT() { return getToken(NewSqlBaseParser.PERCENTLIT, 0); }
		public TerminalNode OUT() { return getToken(NewSqlBaseParser.OUT, 0); }
		public TerminalNode OF() { return getToken(NewSqlBaseParser.OF, 0); }
		public TerminalNode SET() { return getToken(NewSqlBaseParser.SET, 0); }
		public TerminalNode RESET() { return getToken(NewSqlBaseParser.RESET, 0); }
		public TerminalNode VIEW() { return getToken(NewSqlBaseParser.VIEW, 0); }
		public TerminalNode REPLACE() { return getToken(NewSqlBaseParser.REPLACE, 0); }
		public TerminalNode IF() { return getToken(NewSqlBaseParser.IF, 0); }
		public TerminalNode POSITION() { return getToken(NewSqlBaseParser.POSITION, 0); }
		public TerminalNode EXTRACT() { return getToken(NewSqlBaseParser.EXTRACT, 0); }
		public TerminalNode NO() { return getToken(NewSqlBaseParser.NO, 0); }
		public TerminalNode DATA() { return getToken(NewSqlBaseParser.DATA, 0); }
		public TerminalNode START() { return getToken(NewSqlBaseParser.START, 0); }
		public TerminalNode TRANSACTION() { return getToken(NewSqlBaseParser.TRANSACTION, 0); }
		public TerminalNode COMMIT() { return getToken(NewSqlBaseParser.COMMIT, 0); }
		public TerminalNode ROLLBACK() { return getToken(NewSqlBaseParser.ROLLBACK, 0); }
		public TerminalNode IGNORE() { return getToken(NewSqlBaseParser.IGNORE, 0); }
		public TerminalNode SORT() { return getToken(NewSqlBaseParser.SORT, 0); }
		public TerminalNode CLUSTER() { return getToken(NewSqlBaseParser.CLUSTER, 0); }
		public TerminalNode DISTRIBUTE() { return getToken(NewSqlBaseParser.DISTRIBUTE, 0); }
		public TerminalNode UNSET() { return getToken(NewSqlBaseParser.UNSET, 0); }
		public TerminalNode TBLPROPERTIES() { return getToken(NewSqlBaseParser.TBLPROPERTIES, 0); }
		public TerminalNode SKEWED() { return getToken(NewSqlBaseParser.SKEWED, 0); }
		public TerminalNode STORED() { return getToken(NewSqlBaseParser.STORED, 0); }
		public TerminalNode DIRECTORIES() { return getToken(NewSqlBaseParser.DIRECTORIES, 0); }
		public TerminalNode LOCATION() { return getToken(NewSqlBaseParser.LOCATION, 0); }
		public TerminalNode EXCHANGE() { return getToken(NewSqlBaseParser.EXCHANGE, 0); }
		public TerminalNode ARCHIVE() { return getToken(NewSqlBaseParser.ARCHIVE, 0); }
		public TerminalNode UNARCHIVE() { return getToken(NewSqlBaseParser.UNARCHIVE, 0); }
		public TerminalNode FILEFORMAT() { return getToken(NewSqlBaseParser.FILEFORMAT, 0); }
		public TerminalNode TOUCH() { return getToken(NewSqlBaseParser.TOUCH, 0); }
		public TerminalNode COMPACT() { return getToken(NewSqlBaseParser.COMPACT, 0); }
		public TerminalNode CONCATENATE() { return getToken(NewSqlBaseParser.CONCATENATE, 0); }
		public TerminalNode CHANGE() { return getToken(NewSqlBaseParser.CHANGE, 0); }
		public TerminalNode CASCADE() { return getToken(NewSqlBaseParser.CASCADE, 0); }
		public TerminalNode RESTRICT() { return getToken(NewSqlBaseParser.RESTRICT, 0); }
		public TerminalNode BUCKETS() { return getToken(NewSqlBaseParser.BUCKETS, 0); }
		public TerminalNode CLUSTERED() { return getToken(NewSqlBaseParser.CLUSTERED, 0); }
		public TerminalNode SORTED() { return getToken(NewSqlBaseParser.SORTED, 0); }
		public TerminalNode PURGE() { return getToken(NewSqlBaseParser.PURGE, 0); }
		public TerminalNode INPUTFORMAT() { return getToken(NewSqlBaseParser.INPUTFORMAT, 0); }
		public TerminalNode OUTPUTFORMAT() { return getToken(NewSqlBaseParser.OUTPUTFORMAT, 0); }
		public TerminalNode DBPROPERTIES() { return getToken(NewSqlBaseParser.DBPROPERTIES, 0); }
		public TerminalNode DFS() { return getToken(NewSqlBaseParser.DFS, 0); }
		public TerminalNode TRUNCATE() { return getToken(NewSqlBaseParser.TRUNCATE, 0); }
		public TerminalNode COMPUTE() { return getToken(NewSqlBaseParser.COMPUTE, 0); }
		public TerminalNode LIST() { return getToken(NewSqlBaseParser.LIST, 0); }
		public TerminalNode STATISTICS() { return getToken(NewSqlBaseParser.STATISTICS, 0); }
		public TerminalNode ANALYZE() { return getToken(NewSqlBaseParser.ANALYZE, 0); }
		public TerminalNode PARTITIONED() { return getToken(NewSqlBaseParser.PARTITIONED, 0); }
		public TerminalNode EXTERNAL() { return getToken(NewSqlBaseParser.EXTERNAL, 0); }
		public TerminalNode DEFINED() { return getToken(NewSqlBaseParser.DEFINED, 0); }
		public TerminalNode RECORDWRITER() { return getToken(NewSqlBaseParser.RECORDWRITER, 0); }
		public TerminalNode REVOKE() { return getToken(NewSqlBaseParser.REVOKE, 0); }
		public TerminalNode GRANT() { return getToken(NewSqlBaseParser.GRANT, 0); }
		public TerminalNode LOCK() { return getToken(NewSqlBaseParser.LOCK, 0); }
		public TerminalNode UNLOCK() { return getToken(NewSqlBaseParser.UNLOCK, 0); }
		public TerminalNode MSCK() { return getToken(NewSqlBaseParser.MSCK, 0); }
		public TerminalNode REPAIR() { return getToken(NewSqlBaseParser.REPAIR, 0); }
		public TerminalNode RECOVER() { return getToken(NewSqlBaseParser.RECOVER, 0); }
		public TerminalNode EXPORT() { return getToken(NewSqlBaseParser.EXPORT, 0); }
		public TerminalNode IMPORT() { return getToken(NewSqlBaseParser.IMPORT, 0); }
		public TerminalNode LOAD() { return getToken(NewSqlBaseParser.LOAD, 0); }
		public TerminalNode VALUES() { return getToken(NewSqlBaseParser.VALUES, 0); }
		public TerminalNode COMMENT() { return getToken(NewSqlBaseParser.COMMENT, 0); }
		public TerminalNode ROLE() { return getToken(NewSqlBaseParser.ROLE, 0); }
		public TerminalNode ROLES() { return getToken(NewSqlBaseParser.ROLES, 0); }
		public TerminalNode COMPACTIONS() { return getToken(NewSqlBaseParser.COMPACTIONS, 0); }
		public TerminalNode PRINCIPALS() { return getToken(NewSqlBaseParser.PRINCIPALS, 0); }
		public TerminalNode TRANSACTIONS() { return getToken(NewSqlBaseParser.TRANSACTIONS, 0); }
		public TerminalNode INDEX() { return getToken(NewSqlBaseParser.INDEX, 0); }
		public TerminalNode INDEXES() { return getToken(NewSqlBaseParser.INDEXES, 0); }
		public TerminalNode LOCKS() { return getToken(NewSqlBaseParser.LOCKS, 0); }
		public TerminalNode OPTION() { return getToken(NewSqlBaseParser.OPTION, 0); }
		public TerminalNode LOCAL() { return getToken(NewSqlBaseParser.LOCAL, 0); }
		public TerminalNode INPATH() { return getToken(NewSqlBaseParser.INPATH, 0); }
		public TerminalNode ASC() { return getToken(NewSqlBaseParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(NewSqlBaseParser.DESC, 0); }
		public TerminalNode LIMIT() { return getToken(NewSqlBaseParser.LIMIT, 0); }
		public TerminalNode RENAME() { return getToken(NewSqlBaseParser.RENAME, 0); }
		public TerminalNode SETS() { return getToken(NewSqlBaseParser.SETS, 0); }
		public TerminalNode AT() { return getToken(NewSqlBaseParser.AT, 0); }
		public TerminalNode NULLS() { return getToken(NewSqlBaseParser.NULLS, 0); }
		public TerminalNode OVERWRITE() { return getToken(NewSqlBaseParser.OVERWRITE, 0); }
		public TerminalNode ALL() { return getToken(NewSqlBaseParser.ALL, 0); }
		public TerminalNode ANY() { return getToken(NewSqlBaseParser.ANY, 0); }
		public TerminalNode ALTER() { return getToken(NewSqlBaseParser.ALTER, 0); }
		public TerminalNode AS() { return getToken(NewSqlBaseParser.AS, 0); }
		public TerminalNode BETWEEN() { return getToken(NewSqlBaseParser.BETWEEN, 0); }
		public TerminalNode BY() { return getToken(NewSqlBaseParser.BY, 0); }
		public TerminalNode CREATE() { return getToken(NewSqlBaseParser.CREATE, 0); }
		public TerminalNode DELETE() { return getToken(NewSqlBaseParser.DELETE, 0); }
		public TerminalNode DESCRIBE() { return getToken(NewSqlBaseParser.DESCRIBE, 0); }
		public TerminalNode DROP() { return getToken(NewSqlBaseParser.DROP, 0); }
		public TerminalNode EXISTS() { return getToken(NewSqlBaseParser.EXISTS, 0); }
		public TerminalNode FALSE() { return getToken(NewSqlBaseParser.FALSE, 0); }
		public TerminalNode FOR() { return getToken(NewSqlBaseParser.FOR, 0); }
		public TerminalNode GROUP() { return getToken(NewSqlBaseParser.GROUP, 0); }
		public TerminalNode IN() { return getToken(NewSqlBaseParser.IN, 0); }
		public TerminalNode INSERT() { return getToken(NewSqlBaseParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(NewSqlBaseParser.INTO, 0); }
		public TerminalNode IS() { return getToken(NewSqlBaseParser.IS, 0); }
		public TerminalNode LIKE() { return getToken(NewSqlBaseParser.LIKE, 0); }
		public TerminalNode NULL() { return getToken(NewSqlBaseParser.NULL, 0); }
		public TerminalNode ORDER() { return getToken(NewSqlBaseParser.ORDER, 0); }
		public TerminalNode OUTER() { return getToken(NewSqlBaseParser.OUTER, 0); }
		public TerminalNode TABLE() { return getToken(NewSqlBaseParser.TABLE, 0); }
		public TerminalNode TRUE() { return getToken(NewSqlBaseParser.TRUE, 0); }
		public TerminalNode WITH() { return getToken(NewSqlBaseParser.WITH, 0); }
		public TerminalNode RLIKE() { return getToken(NewSqlBaseParser.RLIKE, 0); }
		public TerminalNode AND() { return getToken(NewSqlBaseParser.AND, 0); }
		public TerminalNode CASE() { return getToken(NewSqlBaseParser.CASE, 0); }
		public TerminalNode CAST() { return getToken(NewSqlBaseParser.CAST, 0); }
		public TerminalNode DISTINCT() { return getToken(NewSqlBaseParser.DISTINCT, 0); }
		public TerminalNode DIV() { return getToken(NewSqlBaseParser.DIV, 0); }
		public TerminalNode ELSE() { return getToken(NewSqlBaseParser.ELSE, 0); }
		public TerminalNode END() { return getToken(NewSqlBaseParser.END, 0); }
		public TerminalNode FUNCTION() { return getToken(NewSqlBaseParser.FUNCTION, 0); }
		public TerminalNode INTERVAL() { return getToken(NewSqlBaseParser.INTERVAL, 0); }
		public TerminalNode MACRO() { return getToken(NewSqlBaseParser.MACRO, 0); }
		public TerminalNode OR() { return getToken(NewSqlBaseParser.OR, 0); }
		public TerminalNode STRATIFY() { return getToken(NewSqlBaseParser.STRATIFY, 0); }
		public TerminalNode THEN() { return getToken(NewSqlBaseParser.THEN, 0); }
		public TerminalNode UNBOUNDED() { return getToken(NewSqlBaseParser.UNBOUNDED, 0); }
		public TerminalNode WHEN() { return getToken(NewSqlBaseParser.WHEN, 0); }
		public TerminalNode DATABASE() { return getToken(NewSqlBaseParser.DATABASE, 0); }
		public TerminalNode SELECT() { return getToken(NewSqlBaseParser.SELECT, 0); }
		public TerminalNode FROM() { return getToken(NewSqlBaseParser.FROM, 0); }
		public TerminalNode WHERE() { return getToken(NewSqlBaseParser.WHERE, 0); }
		public TerminalNode HAVING() { return getToken(NewSqlBaseParser.HAVING, 0); }
		public TerminalNode NOT() { return getToken(NewSqlBaseParser.NOT, 0); }
		public TerminalNode DIRECTORY() { return getToken(NewSqlBaseParser.DIRECTORY, 0); }
		public TerminalNode BOTH() { return getToken(NewSqlBaseParser.BOTH, 0); }
		public TerminalNode LEADING() { return getToken(NewSqlBaseParser.LEADING, 0); }
		public TerminalNode TRAILING() { return getToken(NewSqlBaseParser.TRAILING, 0); }
		public NonReservedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonReserved; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).enterNonReserved(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NewSqlBaseListener ) ((NewSqlBaseListener)listener).exitNonReserved(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NewSqlBaseVisitor ) return ((NewSqlBaseVisitor<? extends T>)visitor).visitNonReserved(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonReservedContext nonReserved() throws RecognitionException {
		NonReservedContext _localctx = new NonReservedContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_nonReserved);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2499);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SELECT) | (1L << FROM) | (1L << ADD) | (1L << AS) | (1L << ALL) | (1L << ANY) | (1L << DISTINCT) | (1L << WHERE) | (1L << GROUP) | (1L << BY) | (1L << GROUPING) | (1L << SETS) | (1L << CUBE) | (1L << ROLLUP) | (1L << ORDER) | (1L << HAVING) | (1L << LIMIT) | (1L << AT) | (1L << OR) | (1L << AND) | (1L << IN) | (1L << NOT) | (1L << NO) | (1L << EXISTS) | (1L << BETWEEN) | (1L << LIKE) | (1L << RLIKE) | (1L << IS) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NULLS) | (1L << ASC) | (1L << DESC) | (1L << FOR) | (1L << INTERVAL) | (1L << CASE) | (1L << WHEN) | (1L << THEN) | (1L << ELSE) | (1L << END) | (1L << OUTER) | (1L << PIVOT) | (1L << LATERAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (WINDOW - 64)) | (1L << (OVER - 64)) | (1L << (PARTITION - 64)) | (1L << (RANGE - 64)) | (1L << (ROWS - 64)) | (1L << (UNBOUNDED - 64)) | (1L << (PRECEDING - 64)) | (1L << (FOLLOWING - 64)) | (1L << (CURRENT - 64)) | (1L << (FIRST - 64)) | (1L << (AFTER - 64)) | (1L << (LAST - 64)) | (1L << (ROW - 64)) | (1L << (WITH - 64)) | (1L << (VALUES - 64)) | (1L << (CREATE - 64)) | (1L << (TABLE - 64)) | (1L << (DIRECTORY - 64)) | (1L << (VIEW - 64)) | (1L << (REPLACE - 64)) | (1L << (INSERT - 64)) | (1L << (DELETE - 64)) | (1L << (INTO - 64)) | (1L << (DESCRIBE - 64)) | (1L << (EXPLAIN - 64)) | (1L << (FORMAT - 64)) | (1L << (LOGICAL - 64)) | (1L << (CODEGEN - 64)) | (1L << (COST - 64)) | (1L << (CAST - 64)) | (1L << (SHOW - 64)) | (1L << (TABLES - 64)) | (1L << (COLUMNS - 64)) | (1L << (COLUMN - 64)) | (1L << (USE - 64)) | (1L << (PARTITIONS - 64)) | (1L << (FUNCTIONS - 64)) | (1L << (DROP - 64)) | (1L << (TO - 64)) | (1L << (TABLESAMPLE - 64)) | (1L << (STRATIFY - 64)) | (1L << (ALTER - 64)) | (1L << (RENAME - 64)) | (1L << (ARRAY - 64)) | (1L << (MAP - 64)) | (1L << (STRUCT - 64)) | (1L << (COMMENT - 64)) | (1L << (SET - 64)) | (1L << (RESET - 64)) | (1L << (DATA - 64)) | (1L << (START - 64)) | (1L << (TRANSACTION - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (COMMIT - 128)) | (1L << (ROLLBACK - 128)) | (1L << (MACRO - 128)) | (1L << (IGNORE - 128)) | (1L << (BOTH - 128)) | (1L << (LEADING - 128)) | (1L << (TRAILING - 128)) | (1L << (IF - 128)) | (1L << (POSITION - 128)) | (1L << (EXTRACT - 128)) | (1L << (DIV - 128)) | (1L << (PERCENTLIT - 128)) | (1L << (BUCKET - 128)) | (1L << (OUT - 128)) | (1L << (OF - 128)) | (1L << (SORT - 128)) | (1L << (CLUSTER - 128)) | (1L << (DISTRIBUTE - 128)) | (1L << (OVERWRITE - 128)) | (1L << (TRANSFORM - 128)) | (1L << (REDUCE - 128)) | (1L << (SERDE - 128)) | (1L << (SERDEPROPERTIES - 128)) | (1L << (RECORDREADER - 128)) | (1L << (RECORDWRITER - 128)) | (1L << (DELIMITED - 128)) | (1L << (FIELDS - 128)) | (1L << (TERMINATED - 128)) | (1L << (COLLECTION - 128)) | (1L << (ITEMS - 128)) | (1L << (KEYS - 128)) | (1L << (ESCAPED - 128)) | (1L << (LINES - 128)) | (1L << (SEPARATED - 128)) | (1L << (FUNCTION - 128)) | (1L << (EXTENDED - 128)) | (1L << (REFRESH - 128)) | (1L << (CLEAR - 128)) | (1L << (CACHE - 128)) | (1L << (UNCACHE - 128)) | (1L << (LAZY - 128)) | (1L << (FORMATTED - 128)) | (1L << (GLOBAL - 128)) | (1L << (TEMPORARY - 128)) | (1L << (OPTIONS - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (UNSET - 192)) | (1L << (TBLPROPERTIES - 192)) | (1L << (DBPROPERTIES - 192)) | (1L << (BUCKETS - 192)) | (1L << (SKEWED - 192)) | (1L << (STORED - 192)) | (1L << (DIRECTORIES - 192)) | (1L << (LOCATION - 192)) | (1L << (EXCHANGE - 192)) | (1L << (ARCHIVE - 192)) | (1L << (UNARCHIVE - 192)) | (1L << (FILEFORMAT - 192)) | (1L << (TOUCH - 192)) | (1L << (COMPACT - 192)) | (1L << (CONCATENATE - 192)) | (1L << (CHANGE - 192)) | (1L << (CASCADE - 192)) | (1L << (RESTRICT - 192)) | (1L << (CLUSTERED - 192)) | (1L << (SORTED - 192)) | (1L << (PURGE - 192)) | (1L << (INPUTFORMAT - 192)) | (1L << (OUTPUTFORMAT - 192)) | (1L << (DATABASE - 192)) | (1L << (DATABASES - 192)) | (1L << (DFS - 192)) | (1L << (TRUNCATE - 192)) | (1L << (ANALYZE - 192)) | (1L << (COMPUTE - 192)) | (1L << (LIST - 192)) | (1L << (STATISTICS - 192)) | (1L << (PARTITIONED - 192)) | (1L << (EXTERNAL - 192)) | (1L << (DEFINED - 192)) | (1L << (REVOKE - 192)) | (1L << (GRANT - 192)) | (1L << (LOCK - 192)) | (1L << (UNLOCK - 192)) | (1L << (MSCK - 192)) | (1L << (REPAIR - 192)) | (1L << (RECOVER - 192)) | (1L << (EXPORT - 192)) | (1L << (IMPORT - 192)) | (1L << (LOAD - 192)) | (1L << (ROLE - 192)) | (1L << (ROLES - 192)) | (1L << (COMPACTIONS - 192)) | (1L << (PRINCIPALS - 192)) | (1L << (TRANSACTIONS - 192)) | (1L << (INDEX - 192)) | (1L << (INDEXES - 192)) | (1L << (LOCKS - 192)) | (1L << (OPTION - 192)) | (1L << (LOCAL - 192)) | (1L << (INPATH - 192)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 36:
			return queryTerm_sempred((QueryTermContext)_localctx, predIndex);
		case 75:
			return booleanExpression_sempred((BooleanExpressionContext)_localctx, predIndex);
		case 77:
			return valueExpression_sempred((ValueExpressionContext)_localctx, predIndex);
		case 78:
			return primaryExpression_sempred((PrimaryExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean queryTerm_sempred(QueryTermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return legacy_setops_precedence_enbled;
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return !legacy_setops_precedence_enbled;
		case 4:
			return precpred(_ctx, 1);
		case 5:
			return !legacy_setops_precedence_enbled;
		}
		return true;
	}
	private boolean booleanExpression_sempred(BooleanExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean valueExpression_sempred(ValueExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 6);
		case 9:
			return precpred(_ctx, 5);
		case 10:
			return precpred(_ctx, 4);
		case 11:
			return precpred(_ctx, 3);
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean primaryExpression_sempred(PrimaryExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 5);
		case 15:
			return precpred(_ctx, 3);
		}
		return true;
	}

	private static final int _serializedATNSegments = 2;
	private static final String _serializedATNSegment0 =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0109\u09c8\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\3\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00ef\n\b\3\b\3\b\3\b\5\b\u00f4\n\b\3"+
		"\b\5\b\u00f7\n\b\3\b\3\b\3\b\5\b\u00fc\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\5\b\u0109\n\b\3\b\3\b\5\b\u010d\n\b\3\b\3\b\3\b\3\b\3"+
		"\b\5\b\u0114\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0122"+
		"\n\b\f\b\16\b\u0125\13\b\3\b\5\b\u0128\n\b\3\b\5\b\u012b\n\b\3\b\3\b\3"+
		"\b\3\b\3\b\5\b\u0132\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\7\b\u0143\n\b\f\b\16\b\u0146\13\b\3\b\5\b\u0149\n\b\3\b"+
		"\5\b\u014c\n\b\3\b\3\b\3\b\3\b\3\b\5\b\u0153\n\b\3\b\3\b\3\b\3\b\5\b\u0159"+
		"\n\b\3\b\3\b\3\b\3\b\5\b\u015f\n\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0167\n"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0187\n\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\5\b\u018f\n\b\3\b\3\b\5\b\u0193\n\b\3\b\3\b\3\b\5"+
		"\b\u0198\n\b\3\b\3\b\3\b\3\b\5\b\u019e\n\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b"+
		"\u01a6\n\b\3\b\3\b\3\b\3\b\5\b\u01ac\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\5\b\u01b9\n\b\3\b\6\b\u01bc\n\b\r\b\16\b\u01bd\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\5\b\u01c7\n\b\3\b\6\b\u01ca\n\b\r\b\16\b\u01cb\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u01dc\n\b\3"+
		"\b\3\b\3\b\7\b\u01e1\n\b\f\b\16\b\u01e4\13\b\3\b\5\b\u01e7\n\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\5\b\u01ef\n\b\3\b\3\b\3\b\7\b\u01f4\n\b\f\b\16\b\u01f7"+
		"\13\b\3\b\3\b\3\b\3\b\5\b\u01fd\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\5\b\u020c\n\b\3\b\3\b\5\b\u0210\n\b\3\b\3\b\3\b\3\b"+
		"\5\b\u0216\n\b\3\b\3\b\3\b\3\b\5\b\u021c\n\b\3\b\5\b\u021f\n\b\3\b\5\b"+
		"\u0222\n\b\3\b\3\b\3\b\3\b\5\b\u0228\n\b\3\b\3\b\5\b\u022c\n\b\3\b\3\b"+
		"\5\b\u0230\n\b\3\b\3\b\3\b\5\b\u0235\n\b\3\b\3\b\5\b\u0239\n\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\5\b\u0241\n\b\3\b\5\b\u0244\n\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\5\b\u024d\n\b\3\b\3\b\3\b\5\b\u0252\n\b\3\b\3\b\3\b\3\b\5\b\u0258"+
		"\n\b\3\b\3\b\3\b\3\b\3\b\5\b\u025f\n\b\3\b\5\b\u0262\n\b\3\b\3\b\3\b\3"+
		"\b\5\b\u0268\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0271\n\b\f\b\16\b\u0274"+
		"\13\b\5\b\u0276\n\b\3\b\3\b\5\b\u027a\n\b\3\b\3\b\3\b\5\b\u027f\n\b\3"+
		"\b\3\b\3\b\5\b\u0284\n\b\3\b\3\b\3\b\3\b\3\b\5\b\u028b\n\b\3\b\5\b\u028e"+
		"\n\b\3\b\5\b\u0291\n\b\3\b\3\b\3\b\3\b\3\b\5\b\u0298\n\b\3\b\3\b\3\b\5"+
		"\b\u029d\n\b\3\b\3\b\3\b\5\b\u02a2\n\b\3\b\5\b\u02a5\n\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\5\b\u02ae\n\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u02b6\n\b\3\b"+
		"\3\b\3\b\3\b\5\b\u02bc\n\b\3\b\3\b\5\b\u02c0\n\b\3\b\3\b\5\b\u02c4\n\b"+
		"\3\b\3\b\5\b\u02c8\n\b\5\b\u02ca\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u02d3"+
		"\n\b\3\b\3\b\3\b\3\b\5\b\u02d9\n\b\3\b\3\b\3\b\5\b\u02de\n\b\3\b\5\b\u02e1"+
		"\n\b\3\b\3\b\5\b\u02e5\n\b\3\b\5\b\u02e8\n\b\3\b\3\b\3\b\3\b\3\b\3\b\7"+
		"\b\u02f0\n\b\f\b\16\b\u02f3\13\b\5\b\u02f5\n\b\3\b\3\b\5\b\u02f9\n\b\3"+
		"\b\3\b\3\b\5\b\u02fe\n\b\3\b\5\b\u0301\n\b\3\b\3\b\3\b\3\b\5\b\u0307\n"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u030f\n\b\3\b\3\b\3\b\5\b\u0314\n\b\3\b"+
		"\3\b\3\b\3\b\5\b\u031a\n\b\3\b\3\b\3\b\3\b\5\b\u0320\n\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\7\b\u0329\n\b\f\b\16\b\u032c\13\b\3\b\3\b\3\b\7\b\u0331"+
		"\n\b\f\b\16\b\u0334\13\b\3\b\3\b\7\b\u0338\n\b\f\b\16\b\u033b\13\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\7\b\u0352\n\b\f\b\16\b\u0355\13\b\5\b\u0357\n\b\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\5\n\u0361\n\n\3\n\3\n\5\n\u0365\n\n\3\n\3\n\3\n\3"+
		"\n\3\n\5\n\u036c\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u03da\n\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\5\n\u03e2\n\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u03ea\n\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\5\n\u03f3\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u03ff"+
		"\n\n\3\13\3\13\5\13\u0403\n\13\3\13\5\13\u0406\n\13\3\13\3\13\3\13\3\13"+
		"\5\13\u040c\n\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0416\n\f\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0422\n\r\3\r\3\r\3\r\5\r\u0427"+
		"\n\r\3\16\3\16\3\16\3\17\5\17\u042d\n\17\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u0439\n\20\5\20\u043b\n\20\3\20\3\20\3\20\5"+
		"\20\u0440\n\20\3\20\3\20\5\20\u0444\n\20\3\20\3\20\3\20\5\20\u0449\n\20"+
		"\3\20\3\20\3\20\5\20\u044e\n\20\3\20\5\20\u0451\n\20\3\20\3\20\3\20\5"+
		"\20\u0456\n\20\3\20\3\20\5\20\u045a\n\20\3\20\3\20\3\20\5\20\u045f\n\20"+
		"\5\20\u0461\n\20\3\21\3\21\5\21\u0465\n\21\3\22\3\22\3\22\3\22\3\22\7"+
		"\22\u046c\n\22\f\22\16\22\u046f\13\22\3\22\3\22\3\23\3\23\3\23\5\23\u0476"+
		"\n\23\3\24\3\24\3\24\3\24\3\24\5\24\u047d\n\24\3\25\3\25\3\25\7\25\u0482"+
		"\n\25\f\25\16\25\u0485\13\25\3\26\3\26\3\26\3\26\7\26\u048b\n\26\f\26"+
		"\16\26\u048e\13\26\3\27\3\27\5\27\u0492\n\27\3\27\3\27\3\27\3\27\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\7\31\u049f\n\31\f\31\16\31\u04a2\13\31"+
		"\3\31\3\31\3\32\3\32\5\32\u04a8\n\32\3\32\5\32\u04ab\n\32\3\33\3\33\3"+
		"\33\7\33\u04b0\n\33\f\33\16\33\u04b3\13\33\3\33\5\33\u04b6\n\33\3\34\3"+
		"\34\3\34\3\34\5\34\u04bc\n\34\3\35\3\35\3\35\3\35\7\35\u04c2\n\35\f\35"+
		"\16\35\u04c5\13\35\3\35\3\35\3\36\3\36\3\36\3\36\7\36\u04cd\n\36\f\36"+
		"\16\36\u04d0\13\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u04da"+
		"\n\37\3 \3 \3 \3 \3 \5 \u04e1\n \3!\3!\3!\3!\5!\u04e7\n!\3\"\3\"\3\"\3"+
		"#\5#\u04ed\n#\3#\3#\3#\3#\3#\6#\u04f4\n#\r#\16#\u04f5\5#\u04f8\n#\3$\3"+
		"$\3$\3$\3$\7$\u04ff\n$\f$\16$\u0502\13$\5$\u0504\n$\3$\3$\3$\3$\3$\7$"+
		"\u050b\n$\f$\16$\u050e\13$\5$\u0510\n$\3$\3$\3$\3$\3$\7$\u0517\n$\f$\16"+
		"$\u051a\13$\5$\u051c\n$\3$\3$\3$\3$\3$\7$\u0523\n$\f$\16$\u0526\13$\5"+
		"$\u0528\n$\3$\5$\u052b\n$\3$\3$\3$\5$\u0530\n$\5$\u0532\n$\3%\5%\u0535"+
		"\n%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3&\5&\u0541\n&\3&\3&\3&\3&\3&\5&\u0548"+
		"\n&\3&\3&\3&\3&\3&\5&\u054f\n&\3&\7&\u0552\n&\f&\16&\u0555\13&\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u055f\n\'\3(\3(\5(\u0563\n(\3(\3(\5(\u0567"+
		"\n(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\5)\u0573\n)\3)\5)\u0576\n)\3)\3)\5)"+
		"\u057a\n)\3)\3)\3)\3)\3)\3)\3)\3)\5)\u0584\n)\3)\3)\5)\u0588\n)\5)\u058a"+
		"\n)\3)\5)\u058d\n)\3)\3)\5)\u0591\n)\3)\5)\u0594\n)\3)\3)\5)\u0598\n)"+
		"\3)\3)\7)\u059c\n)\f)\16)\u059f\13)\3)\5)\u05a2\n)\3)\3)\5)\u05a6\n)\3"+
		")\3)\3)\5)\u05ab\n)\3)\5)\u05ae\n)\5)\u05b0\n)\3)\7)\u05b3\n)\f)\16)\u05b6"+
		"\13)\3)\3)\5)\u05ba\n)\3)\5)\u05bd\n)\3)\3)\5)\u05c1\n)\3)\5)\u05c4\n"+
		")\3)\5)\u05c7\n)\5)\u05c9\n)\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3-\3-\3"+
		"-\5-\u05d9\n-\3-\7-\u05dc\n-\f-\16-\u05df\13-\3-\3-\3.\3.\3.\3.\3.\3."+
		"\7.\u05e9\n.\f.\16.\u05ec\13.\3.\3.\5.\u05f0\n.\3/\3/\3/\3/\7/\u05f6\n"+
		"/\f/\16/\u05f9\13/\3/\7/\u05fc\n/\f/\16/\u05ff\13/\3/\5/\u0602\n/\3\60"+
		"\3\60\3\60\3\60\3\60\7\60\u0609\n\60\f\60\16\60\u060c\13\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\7\60\u0618\n\60\f\60\16\60\u061b"+
		"\13\60\3\60\3\60\5\60\u061f\n\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\7\60\u0629\n\60\f\60\16\60\u062c\13\60\3\60\3\60\5\60\u0630\n\60\3"+
		"\61\3\61\3\61\3\61\7\61\u0636\n\61\f\61\16\61\u0639\13\61\5\61\u063b\n"+
		"\61\3\61\3\61\5\61\u063f\n\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62"+
		"\3\62\3\62\7\62\u064b\n\62\f\62\16\62\u064e\13\62\3\62\3\62\3\62\3\63"+
		"\3\63\3\63\3\63\3\63\7\63\u0658\n\63\f\63\16\63\u065b\13\63\3\63\3\63"+
		"\5\63\u065f\n\63\3\64\3\64\5\64\u0663\n\64\3\64\5\64\u0666\n\64\3\65\3"+
		"\65\3\65\5\65\u066b\n\65\3\65\3\65\3\65\3\65\3\65\7\65\u0672\n\65\f\65"+
		"\16\65\u0675\13\65\5\65\u0677\n\65\3\65\3\65\3\65\5\65\u067c\n\65\3\65"+
		"\3\65\3\65\7\65\u0681\n\65\f\65\16\65\u0684\13\65\5\65\u0686\n\65\3\66"+
		"\3\66\3\67\3\67\7\67\u068c\n\67\f\67\16\67\u068f\13\67\38\38\38\38\58"+
		"\u0695\n8\38\38\38\38\38\58\u069c\n8\39\59\u069f\n9\39\39\39\59\u06a4"+
		"\n9\39\39\39\39\59\u06aa\n9\39\39\59\u06ae\n9\39\59\u06b1\n9\39\39\39"+
		"\59\u06b6\n9\3:\3:\3:\3:\3:\3:\3:\7:\u06bf\n:\f:\16:\u06c2\13:\3:\3:\5"+
		":\u06c6\n:\3;\3;\3;\5;\u06cb\n;\3;\3;\3<\5<\u06d0\n<\3<\3<\3<\3<\3<\3"+
		"<\3<\3<\3<\3<\3<\3<\3<\3<\3<\3<\5<\u06e2\n<\5<\u06e4\n<\3<\5<\u06e7\n"+
		"<\3=\3=\3=\3=\3>\3>\3>\7>\u06f0\n>\f>\16>\u06f3\13>\3?\3?\3?\3?\7?\u06f9"+
		"\n?\f?\16?\u06fc\13?\3?\3?\3@\3@\5@\u0702\n@\3A\3A\3A\3A\7A\u0708\nA\f"+
		"A\16A\u070b\13A\3A\3A\3B\3B\3B\5B\u0712\nB\3C\3C\5C\u0716\nC\3C\3C\3C"+
		"\3C\3C\3C\5C\u071e\nC\3C\3C\3C\3C\3C\3C\5C\u0726\nC\3C\3C\3C\3C\5C\u072c"+
		"\nC\3D\3D\3D\3D\7D\u0732\nD\fD\16D\u0735\13D\3D\3D\3E\3E\3E\3E\3E\7E\u073e"+
		"\nE\fE\16E\u0741\13E\5E\u0743\nE\3E\3E\3E\3F\5F\u0749\nF\3F\3F\5F\u074d"+
		"\nF\5F\u074f\nF\3G\3G\3G\3G\3G\3G\3G\5G\u0758\nG\3G\3G\3G\3G\3G\3G\3G"+
		"\3G\3G\3G\5G\u0764\nG\5G\u0766\nG\3G\3G\3G\3G\3G\5G\u076d\nG\3G\3G\3G"+
		"\3G\3G\5G\u0774\nG\3G\3G\3G\3G\5G\u077a\nG\3G\3G\3G\3G\5G\u0780\nG\5G"+
		"\u0782\nG\3H\3H\3H\5H\u0787\nH\3H\3H\3I\3I\3I\5I\u078e\nI\3I\3I\3J\3J"+
		"\5J\u0794\nJ\3J\3J\5J\u0798\nJ\5J\u079a\nJ\3K\3K\3K\7K\u079f\nK\fK\16"+
		"K\u07a2\13K\3L\3L\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\5M\u07b0\nM\5M\u07b2\n"+
		"M\3M\3M\3M\3M\3M\3M\7M\u07ba\nM\fM\16M\u07bd\13M\3N\5N\u07c0\nN\3N\3N"+
		"\3N\3N\3N\3N\5N\u07c8\nN\3N\3N\3N\3N\3N\7N\u07cf\nN\fN\16N\u07d2\13N\3"+
		"N\3N\3N\5N\u07d7\nN\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\5N\u07e3\nN\3N\3N\3"+
		"N\3N\3N\3N\5N\u07eb\nN\3N\3N\3N\3N\5N\u07f1\nN\3N\3N\3N\5N\u07f6\nN\3"+
		"N\3N\3N\5N\u07fb\nN\3O\3O\3O\3O\5O\u0801\nO\3O\3O\3O\3O\3O\3O\3O\3O\3"+
		"O\3O\3O\3O\3O\3O\3O\3O\3O\3O\3O\7O\u0816\nO\fO\16O\u0819\13O\3P\3P\3P"+
		"\6P\u081e\nP\rP\16P\u081f\3P\3P\5P\u0824\nP\3P\3P\3P\3P\3P\6P\u082b\n"+
		"P\rP\16P\u082c\3P\3P\5P\u0831\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3"+
		"P\3P\7P\u0841\nP\fP\16P\u0844\13P\5P\u0846\nP\3P\3P\3P\3P\3P\3P\5P\u084e"+
		"\nP\3P\3P\3P\3P\3P\3P\3P\5P\u0857\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\3P\3P\6P\u086c\nP\rP\16P\u086d\3P\3P\3P\3P\3P\3P\3"+
		"P\3P\3P\5P\u0879\nP\3P\3P\3P\7P\u087e\nP\fP\16P\u0881\13P\5P\u0883\nP"+
		"\3P\3P\3P\5P\u0888\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\6P"+
		"\u0899\nP\rP\16P\u089a\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\5"+
		"P\u08ac\nP\3P\3P\3P\3P\3P\3P\3P\3P\7P\u08b6\nP\fP\16P\u08b9\13P\3Q\3Q"+
		"\3Q\3Q\3Q\3Q\3Q\3Q\6Q\u08c3\nQ\rQ\16Q\u08c4\5Q\u08c7\nQ\3R\3R\3S\3S\3"+
		"T\3T\3U\3U\3V\3V\7V\u08d3\nV\fV\16V\u08d6\13V\3W\3W\3W\3W\5W\u08dc\nW"+
		"\3X\5X\u08df\nX\3X\3X\5X\u08e3\nX\3Y\3Y\3Y\5Y\u08e8\nY\3Z\3Z\3Z\3Z\3Z"+
		"\3Z\3Z\3Z\3Z\3Z\3Z\3Z\3Z\3Z\3Z\5Z\u08f9\nZ\3Z\3Z\5Z\u08fd\nZ\3Z\3Z\3Z"+
		"\3Z\3Z\7Z\u0904\nZ\fZ\16Z\u0907\13Z\3Z\5Z\u090a\nZ\5Z\u090c\nZ\3[\3[\3"+
		"[\7[\u0911\n[\f[\16[\u0914\13[\3\\\3\\\3\\\3\\\5\\\u091a\n\\\3]\3]\3]"+
		"\7]\u091f\n]\f]\16]\u0922\13]\3^\3^\3^\3^\3^\5^\u0929\n^\3_\3_\3_\3_\3"+
		"_\3`\3`\3`\3`\7`\u0934\n`\f`\16`\u0937\13`\3a\3a\3a\3a\3b\3b\3b\3b\3b"+
		"\3b\3b\3b\3b\3b\3b\7b\u0948\nb\fb\16b\u094b\13b\3b\3b\3b\3b\3b\7b\u0952"+
		"\nb\fb\16b\u0955\13b\5b\u0957\nb\3b\3b\3b\3b\3b\7b\u095e\nb\fb\16b\u0961"+
		"\13b\5b\u0963\nb\5b\u0965\nb\3b\5b\u0968\nb\3b\5b\u096b\nb\3c\3c\3c\3"+
		"c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\5c\u097d\nc\3d\3d\3d\3d\3d\3d\3"+
		"d\5d\u0986\nd\3e\3e\3e\7e\u098b\ne\fe\16e\u098e\13e\3f\3f\3f\3f\3f\3f"+
		"\3f\3f\3f\3f\3f\3f\3f\3f\3f\5f\u099f\nf\3g\3g\3g\5g\u09a4\ng\3h\3h\3i"+
		"\5i\u09a9\ni\3i\3i\5i\u09ad\ni\3i\3i\5i\u09b1\ni\3i\3i\5i\u09b5\ni\3i"+
		"\3i\5i\u09b9\ni\3i\3i\5i\u09bd\ni\3i\3i\5i\u09c1\ni\3i\5i\u09c4\ni\3j"+
		"\3j\3j\7\u02f1\u032a\u0332\u0339\u0353\6J\u0098\u009c\u009ek\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\"+
		"^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090"+
		"\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8"+
		"\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0"+
		"\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\2\36\3\2\u00d2"+
		"\u00d3\4\2ZZ\\\\\5\2df\u00b8\u00b8\u00be\u00be\4\2\16\16!!\4\2..aa\4\2"+
		"\u00b8\u00b8\u00be\u00be\4\2\17\17\u00df\u00df\4\2TTVW\3\2ps\3\2pr\3\2"+
		"-.\4\2KKMM\4\2\21\21\23\23\3\2\u00fe\u00ff\3\2&\'\4\2\u0094\u0095\u009a"+
		"\u009a\3\2\u0096\u0099\4\2\u0094\u0095\u009d\u009d\3\2\u0086\u0088\3\2"+
		"\u008c\u0093\3\2\u0094\u009e\3\2\37\"\3\2*+\3\2\u0094\u0095\4\2DD\u00a5"+
		"\u00a5\4\2\33\33\u00a3\u00a3\3\2HI\13\2\r\6588@OXot\u008b\u0099\u0099"+
		"\u009f\u00a8\u00aa\u00f6\u00f8\u00f9\2\u0b63\2\u00d4\3\2\2\2\4\u00d7\3"+
		"\2\2\2\6\u00da\3\2\2\2\b\u00dd\3\2\2\2\n\u00e0\3\2\2\2\f\u00e3\3\2\2\2"+
		"\16\u0356\3\2\2\2\20\u0358\3\2\2\2\22\u03fe\3\2\2\2\24\u0400\3\2\2\2\26"+
		"\u040f\3\2\2\2\30\u041b\3\2\2\2\32\u0428\3\2\2\2\34\u042c\3\2\2\2\36\u0460"+
		"\3\2\2\2 \u0462\3\2\2\2\"\u0466\3\2\2\2$\u0472\3\2\2\2&\u047c\3\2\2\2"+
		"(\u047e\3\2\2\2*\u0486\3\2\2\2,\u048f\3\2\2\2.\u0497\3\2\2\2\60\u049a"+
		"\3\2\2\2\62\u04a5\3\2\2\2\64\u04b5\3\2\2\2\66\u04bb\3\2\2\28\u04bd\3\2"+
		"\2\2:\u04c8\3\2\2\2<\u04d9\3\2\2\2>\u04e0\3\2\2\2@\u04e2\3\2\2\2B\u04e8"+
		"\3\2\2\2D\u04f7\3\2\2\2F\u0503\3\2\2\2H\u0534\3\2\2\2J\u0539\3\2\2\2L"+
		"\u055e\3\2\2\2N\u0560\3\2\2\2P\u05c8\3\2\2\2R\u05ca\3\2\2\2T\u05cd\3\2"+
		"\2\2V\u05d1\3\2\2\2X\u05d5\3\2\2\2Z\u05ef\3\2\2\2\\\u05f1\3\2\2\2^\u062f"+
		"\3\2\2\2`\u063e\3\2\2\2b\u0640\3\2\2\2d\u065e\3\2\2\2f\u0660\3\2\2\2h"+
		"\u0667\3\2\2\2j\u0687\3\2\2\2l\u0689\3\2\2\2n\u069b\3\2\2\2p\u06b5\3\2"+
		"\2\2r\u06c5\3\2\2\2t\u06c7\3\2\2\2v\u06e6\3\2\2\2x\u06e8\3\2\2\2z\u06ec"+
		"\3\2\2\2|\u06f4\3\2\2\2~\u06ff\3\2\2\2\u0080\u0703\3\2\2\2\u0082\u070e"+
		"\3\2\2\2\u0084\u072b\3\2\2\2\u0086\u072d\3\2\2\2\u0088\u0738\3\2\2\2\u008a"+
		"\u074e\3\2\2\2\u008c\u0781\3\2\2\2\u008e\u0786\3\2\2\2\u0090\u078d\3\2"+
		"\2\2\u0092\u0791\3\2\2\2\u0094\u079b\3\2\2\2\u0096\u07a3\3\2\2\2\u0098"+
		"\u07b1\3\2\2\2\u009a\u07fa\3\2\2\2\u009c\u0800\3\2\2\2\u009e\u08ab\3\2"+
		"\2\2\u00a0\u08c6\3\2\2\2\u00a2\u08c8\3\2\2\2\u00a4\u08ca\3\2\2\2\u00a6"+
		"\u08cc\3\2\2\2\u00a8\u08ce\3\2\2\2\u00aa\u08d0\3\2\2\2\u00ac\u08d7\3\2"+
		"\2\2\u00ae\u08e2\3\2\2\2\u00b0\u08e7\3\2\2\2\u00b2\u090b\3\2\2\2\u00b4"+
		"\u090d\3\2\2\2\u00b6\u0915\3\2\2\2\u00b8\u091b\3\2\2\2\u00ba\u0923\3\2"+
		"\2\2\u00bc\u092a\3\2\2\2\u00be\u092f\3\2\2\2\u00c0\u0938\3\2\2\2\u00c2"+
		"\u096a\3\2\2\2\u00c4\u097c\3\2\2\2\u00c6\u0985\3\2\2\2\u00c8\u0987\3\2"+
		"\2\2\u00ca\u099e\3\2\2\2\u00cc\u09a3\3\2\2\2\u00ce\u09a5\3\2\2\2\u00d0"+
		"\u09c3\3\2\2\2\u00d2\u09c5\3\2\2\2\u00d4\u00d5\5\16\b\2\u00d5\u00d6\7"+
		"\2\2\3\u00d6\3\3\2\2\2\u00d7\u00d8\5\u0092J\2\u00d8\u00d9\7\2\2\3\u00d9"+
		"\5\3\2\2\2\u00da\u00db\5\u008eH\2\u00db\u00dc\7\2\2\3\u00dc\7\3\2\2\2"+
		"\u00dd\u00de\5\u0090I\2\u00de\u00df\7\2\2\3\u00df\t\3\2\2\2\u00e0\u00e1"+
		"\5\u00b2Z\2\u00e1\u00e2\7\2\2\3\u00e2\13\3\2\2\2\u00e3\u00e4\5\u00b4["+
		"\2\u00e4\u00e5\7\2\2\3\u00e5\r\3\2\2\2\u00e6\u0357\5\34\17\2\u00e7\u00e8"+
		"\7l\2\2\u00e8\u0357\5\u00caf\2\u00e9\u00ea\7Y\2\2\u00ea\u00ee\7\u00d9"+
		"\2\2\u00eb\u00ec\7\u0089\2\2\u00ec\u00ed\7\"\2\2\u00ed\u00ef\7$\2\2\u00ee"+
		"\u00eb\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f3\5\u00ca"+
		"f\2\u00f1\u00f2\7|\2\2\u00f2\u00f4\7\u00fa\2\2\u00f3\u00f1\3\2\2\2\u00f3"+
		"\u00f4\3\2\2\2\u00f4\u00f6\3\2\2\2\u00f5\u00f7\5\32\16\2\u00f6\u00f5\3"+
		"\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00fb\3\2\2\2\u00f8\u00f9\7O\2\2\u00f9"+
		"\u00fa\7\u00c4\2\2\u00fa\u00fc\5\60\31\2\u00fb\u00f8\3\2\2\2\u00fb\u00fc"+
		"\3\2\2\2\u00fc\u0357\3\2\2\2\u00fd\u00fe\7w\2\2\u00fe\u00ff\7\u00d9\2"+
		"\2\u00ff\u0100\5\u00caf\2\u0100\u0101\7}\2\2\u0101\u0102\7\u00c4\2\2\u0102"+
		"\u0103\5\60\31\2\u0103\u0357\3\2\2\2\u0104\u0105\7o\2\2\u0105\u0108\7"+
		"\u00d9\2\2\u0106\u0107\7\u0089\2\2\u0107\u0109\7$\2\2\u0108\u0106\3\2"+
		"\2\2\u0108\u0109\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010c\5\u00caf\2\u010b"+
		"\u010d\t\2\2\2\u010c\u010b\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u0357\3\2"+
		"\2\2\u010e\u0113\5\24\13\2\u010f\u0110\7\3\2\2\u0110\u0111\5\u00b4[\2"+
		"\u0111\u0112\7\4\2\2\u0112\u0114\3\2\2\2\u0113\u010f\3\2\2\2\u0113\u0114"+
		"\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0123\5.\30\2\u0116\u0117\7\u00c1\2"+
		"\2\u0117\u0122\5\60\31\2\u0118\u0119\7\u00e1\2\2\u0119\u011a\7\26\2\2"+
		"\u011a\u0122\5x=\2\u011b\u0122\5\26\f\2\u011c\u0122\5\32\16\2\u011d\u011e"+
		"\7|\2\2\u011e\u0122\7\u00fa\2\2\u011f\u0120\7\u00c3\2\2\u0120\u0122\5"+
		"\60\31\2\u0121\u0116\3\2\2\2\u0121\u0118\3\2\2\2\u0121\u011b\3\2\2\2\u0121"+
		"\u011c\3\2\2\2\u0121\u011d\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0125\3\2"+
		"\2\2\u0123\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u012a\3\2\2\2\u0125"+
		"\u0123\3\2\2\2\u0126\u0128\7\20\2\2\u0127\u0126\3\2\2\2\u0127\u0128\3"+
		"\2\2\2\u0128\u0129\3\2\2\2\u0129\u012b\5\34\17\2\u012a\u0127\3\2\2\2\u012a"+
		"\u012b\3\2\2\2\u012b\u0357\3\2\2\2\u012c\u0131\5\24\13\2\u012d\u012e\7"+
		"\3\2\2\u012e\u012f\5\u00b4[\2\u012f\u0130\7\4\2\2\u0130\u0132\3\2\2\2"+
		"\u0131\u012d\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0144\3\2\2\2\u0133\u0134"+
		"\7|\2\2\u0134\u0143\7\u00fa\2\2\u0135\u0136\7\u00e1\2\2\u0136\u0137\7"+
		"\26\2\2\u0137\u0138\7\3\2\2\u0138\u0139\5\u00b4[\2\u0139\u013a\7\4\2\2"+
		"\u013a\u0143\3\2\2\2\u013b\u0143\5\26\f\2\u013c\u0143\5\30\r\2\u013d\u0143"+
		"\5\u008cG\2\u013e\u0143\5<\37\2\u013f\u0143\5\32\16\2\u0140\u0141\7\u00c3"+
		"\2\2\u0141\u0143\5\60\31\2\u0142\u0133\3\2\2\2\u0142\u0135\3\2\2\2\u0142"+
		"\u013b\3\2\2\2\u0142\u013c\3\2\2\2\u0142\u013d\3\2\2\2\u0142\u013e\3\2"+
		"\2\2\u0142\u013f\3\2\2\2\u0142\u0140\3\2\2\2\u0143\u0146\3\2\2\2\u0144"+
		"\u0142\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u014b\3\2\2\2\u0146\u0144\3\2"+
		"\2\2\u0147\u0149\7\20\2\2\u0148\u0147\3\2\2\2\u0148\u0149\3\2\2\2\u0149"+
		"\u014a\3\2\2\2\u014a\u014c\5\34\17\2\u014b\u0148\3\2\2\2\u014b\u014c\3"+
		"\2\2\2\u014c\u0357\3\2\2\2\u014d\u014e\7Y\2\2\u014e\u0152\7Z\2\2\u014f"+
		"\u0150\7\u0089\2\2\u0150\u0151\7\"\2\2\u0151\u0153\7$\2\2\u0152\u014f"+
		"\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0155\5\u008eH"+
		"\2\u0155\u0156\7&\2\2\u0156\u0158\5\u008eH\2\u0157\u0159\5\32\16\2\u0158"+
		"\u0157\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u0357\3\2\2\2\u015a\u015b\7\u00dd"+
		"\2\2\u015b\u015c\7Z\2\2\u015c\u015e\5\u008eH\2\u015d\u015f\5\"\22\2\u015e"+
		"\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0160\3\2\2\2\u0160\u0161\7\u00de"+
		"\2\2\u0161\u0166\7\u00e0\2\2\u0162\u0167\5\u00caf\2\u0163\u0164\7/\2\2"+
		"\u0164\u0165\7j\2\2\u0165\u0167\5z>\2\u0166\u0162\3\2\2\2\u0166\u0163"+
		"\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u0357\3\2\2\2\u0168\u0169\7w\2\2\u0169"+
		"\u016a\7Z\2\2\u016a\u016b\5\u008eH\2\u016b\u016c\7\17\2\2\u016c\u016d"+
		"\7j\2\2\u016d\u016e\7\3\2\2\u016e\u016f\5\u00b4[\2\u016f\u0170\7\4\2\2"+
		"\u0170\u0357\3\2\2\2\u0171\u0172\7w\2\2\u0172\u0173\t\3\2\2\u0173\u0174"+
		"\5\u008eH\2\u0174\u0175\7x\2\2\u0175\u0176\7t\2\2\u0176\u0177\5\u008e"+
		"H\2\u0177\u0357\3\2\2\2\u0178\u0179\7w\2\2\u0179\u017a\t\3\2\2\u017a\u017b"+
		"\5\u008eH\2\u017b\u017c\7}\2\2\u017c\u017d\7\u00c3\2\2\u017d\u017e\5\60"+
		"\31\2\u017e\u0357\3\2\2\2\u017f\u0180\7w\2\2\u0180\u0181\t\3\2\2\u0181"+
		"\u0182\5\u008eH\2\u0182\u0183\7\u00c2\2\2\u0183\u0186\7\u00c3\2\2\u0184"+
		"\u0185\7\u0089\2\2\u0185\u0187\7$\2\2\u0186\u0184\3\2\2\2\u0186\u0187"+
		"\3\2\2\2\u0187\u0188\3\2\2\2\u0188\u0189\5\60\31\2\u0189\u0357\3\2\2\2"+
		"\u018a\u018b\7w\2\2\u018b\u018c\7Z\2\2\u018c\u018e\5\u008eH\2\u018d\u018f"+
		"\5\"\22\2\u018e\u018d\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u0190\3\2\2\2"+
		"\u0190\u0192\7\u00d1\2\2\u0191\u0193\7k\2\2\u0192\u0191\3\2\2\2\u0192"+
		"\u0193\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0195\5\u00caf\2\u0195\u0197"+
		"\5\u00b6\\\2\u0196\u0198\5\u00b0Y\2\u0197\u0196\3\2\2\2\u0197\u0198\3"+
		"\2\2\2\u0198\u0357\3\2\2\2\u0199\u019a\7w\2\2\u019a\u019b\7Z\2\2\u019b"+
		"\u019d\5\u008eH\2\u019c\u019e\5\"\22\2\u019d\u019c\3\2\2\2\u019d\u019e"+
		"\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\7}\2\2\u01a0\u01a1\7\u00aa\2"+
		"\2\u01a1\u01a5\7\u00fa\2\2\u01a2\u01a3\7O\2\2\u01a3\u01a4\7\u00ab\2\2"+
		"\u01a4\u01a6\5\60\31\2\u01a5\u01a2\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u0357"+
		"\3\2\2\2\u01a7\u01a8\7w\2\2\u01a8\u01a9\7Z\2\2\u01a9\u01ab\5\u008eH\2"+
		"\u01aa\u01ac\5\"\22\2\u01ab\u01aa\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01ad"+
		"\3\2\2\2\u01ad\u01ae\7}\2\2\u01ae\u01af\7\u00ab\2\2\u01af\u01b0\5\60\31"+
		"\2\u01b0\u0357\3\2\2\2\u01b1\u01b2\7w\2\2\u01b2\u01b3\7Z\2\2\u01b3\u01b4"+
		"\5\u008eH\2\u01b4\u01b8\7\17\2\2\u01b5\u01b6\7\u0089\2\2\u01b6\u01b7\7"+
		"\"\2\2\u01b7\u01b9\7$\2\2\u01b8\u01b5\3\2\2\2\u01b8\u01b9\3\2\2\2\u01b9"+
		"\u01bb\3\2\2\2\u01ba\u01bc\5 \21\2\u01bb\u01ba\3\2\2\2\u01bc\u01bd\3\2"+
		"\2\2\u01bd\u01bb\3\2\2\2\u01bd\u01be\3\2\2\2\u01be\u0357\3\2\2\2\u01bf"+
		"\u01c0\7w\2\2\u01c0\u01c1\7\\\2\2\u01c1\u01c2\5\u008eH\2\u01c2\u01c6\7"+
		"\17\2\2\u01c3\u01c4\7\u0089\2\2\u01c4\u01c5\7\"\2\2\u01c5\u01c7\7$\2\2"+
		"\u01c6\u01c3\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c9\3\2\2\2\u01c8\u01ca"+
		"\5\"\22\2\u01c9\u01c8\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01c9\3\2\2\2"+
		"\u01cb\u01cc\3\2\2\2\u01cc\u0357\3\2\2\2\u01cd\u01ce\7w\2\2\u01ce\u01cf"+
		"\7Z\2\2\u01cf\u01d0\5\u008eH\2\u01d0\u01d1\5\"\22\2\u01d1\u01d2\7x\2\2"+
		"\u01d2\u01d3\7t\2\2\u01d3\u01d4\5\"\22\2\u01d4\u0357\3\2\2\2\u01d5\u01d6"+
		"\7w\2\2\u01d6\u01d7\7Z\2\2\u01d7\u01d8\5\u008eH\2\u01d8\u01db\7o\2\2\u01d9"+
		"\u01da\7\u0089\2\2\u01da\u01dc\7$\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc"+
		"\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01e2\5\"\22\2\u01de\u01df\7\5\2\2"+
		"\u01df\u01e1\5\"\22\2\u01e0\u01de\3\2\2\2\u01e1\u01e4\3\2\2\2\u01e2\u01e0"+
		"\3\2\2\2\u01e2\u01e3\3\2\2\2\u01e3\u01e6\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e5"+
		"\u01e7\7\u00d6\2\2\u01e6\u01e5\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7\u0357"+
		"\3\2\2\2\u01e8\u01e9\7w\2\2\u01e9\u01ea\7\\\2\2\u01ea\u01eb\5\u008eH\2"+
		"\u01eb\u01ee\7o\2\2\u01ec\u01ed\7\u0089\2\2\u01ed\u01ef\7$\2\2\u01ee\u01ec"+
		"\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0\u01f5\5\"\22\2"+
		"\u01f1\u01f2\7\5\2\2\u01f2\u01f4\5\"\22\2\u01f3\u01f1\3\2\2\2\u01f4\u01f7"+
		"\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u0357\3\2\2\2\u01f7"+
		"\u01f5\3\2\2\2\u01f8\u01f9\7w\2\2\u01f9\u01fa\7Z\2\2\u01fa\u01fc\5\u008e"+
		"H\2\u01fb\u01fd\5\"\22\2\u01fc\u01fb\3\2\2\2\u01fc\u01fd\3\2\2\2\u01fd"+
		"\u01fe\3\2\2\2\u01fe\u01ff\7}\2\2\u01ff\u0200\5\32\16\2\u0200\u0357\3"+
		"\2\2\2\u0201\u0202\7w\2\2\u0202\u0203\7Z\2\2\u0203\u0204\5\u008eH\2\u0204"+
		"\u0205\7\u00ea\2\2\u0205\u0206\7m\2\2\u0206\u0357\3\2\2\2\u0207\u0208"+
		"\7o\2\2\u0208\u020b\7Z\2\2\u0209\u020a\7\u0089\2\2\u020a\u020c\7$\2\2"+
		"\u020b\u0209\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020d\3\2\2\2\u020d\u020f"+
		"\5\u008eH\2\u020e\u0210\7\u00d6\2\2\u020f\u020e\3\2\2\2\u020f\u0210\3"+
		"\2\2\2\u0210\u0357\3\2\2\2\u0211\u0212\7o\2\2\u0212\u0215\7\\\2\2\u0213"+
		"\u0214\7\u0089\2\2\u0214\u0216\7$\2\2\u0215\u0213\3\2\2\2\u0215\u0216"+
		"\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0357\5\u008eH\2\u0218\u021b\7Y\2\2"+
		"\u0219\u021a\7\37\2\2\u021a\u021c\7]\2\2\u021b\u0219\3\2\2\2\u021b\u021c"+
		"\3\2\2\2\u021c\u0221\3\2\2\2\u021d\u021f\7\u00bf\2\2\u021e\u021d\3\2\2"+
		"\2\u021e\u021f\3\2\2\2\u021f\u0220\3\2\2\2\u0220\u0222\7\u00c0\2\2\u0221"+
		"\u021e\3\2\2\2\u0221\u0222\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u0227\7\\"+
		"\2\2\u0224\u0225\7\u0089\2\2\u0225\u0226\7\"\2\2\u0226\u0228\7$\2\2\u0227"+
		"\u0224\3\2\2\2\u0227\u0228\3\2\2\2\u0228\u0229\3\2\2\2\u0229\u022b\5\u008e"+
		"H\2\u022a\u022c\5\u0080A\2\u022b\u022a\3\2\2\2\u022b\u022c\3\2\2\2\u022c"+
		"\u022f\3\2\2\2\u022d\u022e\7|\2\2\u022e\u0230\7\u00fa\2\2\u022f\u022d"+
		"\3\2\2\2\u022f\u0230\3\2\2\2\u0230\u0234\3\2\2\2\u0231\u0232\7\u00e1\2"+
		"\2\u0232\u0233\7?\2\2\u0233\u0235\5x=\2\u0234\u0231\3\2\2\2\u0234\u0235"+
		"\3\2\2\2\u0235\u0238\3\2\2\2\u0236\u0237\7\u00c3\2\2\u0237\u0239\5\60"+
		"\31\2\u0238\u0236\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u023a\3\2\2\2\u023a"+
		"\u023b\7\20\2\2\u023b\u023c\5\34\17\2\u023c\u0357\3\2\2\2\u023d\u0240"+
		"\7Y\2\2\u023e\u023f\7\37\2\2\u023f\u0241\7]\2\2\u0240\u023e\3\2\2\2\u0240"+
		"\u0241\3\2\2\2\u0241\u0243\3\2\2\2\u0242\u0244\7\u00bf\2\2\u0243\u0242"+
		"\3\2\2\2\u0243\u0244\3\2\2\2\u0244\u0245\3\2\2\2\u0245\u0246\7\u00c0\2"+
		"\2\u0246\u0247\7\\\2\2\u0247\u024c\5\u008eH\2\u0248\u0249\7\3\2\2\u0249"+
		"\u024a\5\u00b4[\2\u024a\u024b\7\4\2\2\u024b\u024d\3\2\2\2\u024c\u0248"+
		"\3\2\2\2\u024c\u024d\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u0251\5.\30\2\u024f"+
		"\u0250\7\u00c1\2\2\u0250\u0252\5\60\31\2\u0251\u024f\3\2\2\2\u0251\u0252"+
		"\3\2\2\2\u0252\u0357\3\2\2\2\u0253\u0254\7w\2\2\u0254\u0255\7\\\2\2\u0255"+
		"\u0257\5\u008eH\2\u0256\u0258\7\20\2\2\u0257\u0256\3\2\2\2\u0257\u0258"+
		"\3\2\2\2\u0258\u0259\3\2\2\2\u0259\u025a\5\34\17\2\u025a\u0357\3\2\2\2"+
		"\u025b\u025e\7Y\2\2\u025c\u025d\7\37\2\2\u025d\u025f\7]\2\2\u025e\u025c"+
		"\3\2\2\2\u025e\u025f\3\2\2\2\u025f\u0261\3\2\2\2\u0260\u0262\7\u00c0\2"+
		"\2\u0261\u0260\3\2\2\2\u0261\u0262\3\2\2\2\u0262\u0263\3\2\2\2\u0263\u0267"+
		"\7\u00b7\2\2\u0264\u0265\7\u0089\2\2\u0265\u0266\7\"\2\2\u0266\u0268\7"+
		"$\2\2\u0267\u0264\3\2\2\2\u0267\u0268\3\2\2\2\u0268\u0269\3\2\2\2\u0269"+
		"\u026a\5\u00c8e\2\u026a\u026b\7\20\2\2\u026b\u0275\7\u00fa\2\2\u026c\u026d"+
		"\7\u00a9\2\2\u026d\u0272\5B\"\2\u026e\u026f\7\5\2\2\u026f\u0271\5B\"\2"+
		"\u0270\u026e\3\2\2\2\u0271\u0274\3\2\2\2\u0272\u0270\3\2\2\2\u0272\u0273"+
		"\3\2\2\2\u0273\u0276\3\2\2\2\u0274\u0272\3\2\2\2\u0275\u026c\3\2\2\2\u0275"+
		"\u0276\3\2\2\2\u0276\u0357\3\2\2\2\u0277\u0279\7o\2\2\u0278\u027a\7\u00c0"+
		"\2\2\u0279\u0278\3\2\2\2\u0279\u027a\3\2\2\2\u027a\u027b\3\2\2\2\u027b"+
		"\u027e\7\u00b7\2\2\u027c\u027d\7\u0089\2\2\u027d\u027f\7$\2\2\u027e\u027c"+
		"\3\2\2\2\u027e\u027f\3\2\2\2\u027f\u0280\3\2\2\2\u0280\u0357\5\u00c8e"+
		"\2\u0281\u0283\7b\2\2\u0282\u0284\t\4\2\2\u0283\u0282\3\2\2\2\u0283\u0284"+
		"\3\2\2\2\u0284\u0285\3\2\2\2\u0285\u0357\5\16\b\2\u0286\u0287\7h\2\2\u0287"+
		"\u028a\7i\2\2\u0288\u0289\t\5\2\2\u0289\u028b\5\u00caf\2\u028a\u0288\3"+
		"\2\2\2\u028a\u028b\3\2\2\2\u028b\u0290\3\2\2\2\u028c\u028e\7&\2\2\u028d"+
		"\u028c\3\2\2\2\u028d\u028e\3\2\2\2\u028e\u028f\3\2\2\2\u028f\u0291\7\u00fa"+
		"\2\2\u0290\u028d\3\2\2\2\u0290\u0291\3\2\2\2\u0291\u0357\3\2\2\2\u0292"+
		"\u0293\7h\2\2\u0293\u0294\7Z\2\2\u0294\u0297\7\u00b8\2\2\u0295\u0296\t"+
		"\5\2\2\u0296\u0298\5\u00caf\2\u0297\u0295\3\2\2\2\u0297\u0298\3\2\2\2"+
		"\u0298\u0299\3\2\2\2\u0299\u029a\7&\2\2\u029a\u029c\7\u00fa\2\2\u029b"+
		"\u029d\5\"\22\2\u029c\u029b\3\2\2\2\u029c\u029d\3\2\2\2\u029d\u0357\3"+
		"\2\2\2\u029e\u029f\7h\2\2\u029f\u02a4\7\u00da\2\2\u02a0\u02a2\7&\2\2\u02a1"+
		"\u02a0\3\2\2\2\u02a1\u02a2\3\2\2\2\u02a2\u02a3\3\2\2\2\u02a3\u02a5\7\u00fa"+
		"\2\2\u02a4\u02a1\3\2\2\2\u02a4\u02a5\3\2\2\2\u02a5\u0357\3\2\2\2\u02a6"+
		"\u02a7\7h\2\2\u02a7\u02a8\7\u00c3\2\2\u02a8\u02ad\5\u008eH\2\u02a9\u02aa"+
		"\7\3\2\2\u02aa\u02ab\5\64\33\2\u02ab\u02ac\7\4\2\2\u02ac\u02ae\3\2\2\2"+
		"\u02ad\u02a9\3\2\2\2\u02ad\u02ae\3\2\2\2\u02ae\u0357\3\2\2\2\u02af\u02b0"+
		"\7h\2\2\u02b0\u02b1\7j\2\2\u02b1\u02b2\t\5\2\2\u02b2\u02b5\5\u008eH\2"+
		"\u02b3\u02b4\t\5\2\2\u02b4\u02b6\5\u00caf\2\u02b5\u02b3\3\2\2\2\u02b5"+
		"\u02b6\3\2\2\2\u02b6\u0357\3\2\2\2\u02b7\u02b8\7h\2\2\u02b8\u02b9\7m\2"+
		"\2\u02b9\u02bb\5\u008eH\2\u02ba\u02bc\5\"\22\2\u02bb\u02ba\3\2\2\2\u02bb"+
		"\u02bc\3\2\2\2\u02bc\u0357\3\2\2\2\u02bd\u02bf\7h\2\2\u02be\u02c0\5\u00ca"+
		"f\2\u02bf\u02be\3\2\2\2\u02bf\u02c0\3\2\2\2\u02c0\u02c1\3\2\2\2\u02c1"+
		"\u02c9\7n\2\2\u02c2\u02c4\7&\2\2\u02c3\u02c2\3\2\2\2\u02c3\u02c4\3\2\2"+
		"\2\u02c4\u02c7\3\2\2\2\u02c5\u02c8\5\u00c8e\2\u02c6\u02c8\7\u00fa\2\2"+
		"\u02c7\u02c5\3\2\2\2\u02c7\u02c6\3\2\2\2\u02c8\u02ca\3\2\2\2\u02c9\u02c3"+
		"\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u0357\3\2\2\2\u02cb\u02cc\7h\2\2\u02cc"+
		"\u02cd\7Y\2\2\u02cd\u02ce\7Z\2\2\u02ce\u0357\5\u008eH\2\u02cf\u02d0\t"+
		"\6\2\2\u02d0\u02d2\7\u00b7\2\2\u02d1\u02d3\7\u00b8\2\2\u02d2\u02d1\3\2"+
		"\2\2\u02d2\u02d3\3\2\2\2\u02d3\u02d4\3\2\2\2\u02d4\u0357\5&\24\2\u02d5"+
		"\u02d6\t\6\2\2\u02d6\u02d8\7\u00d9\2\2\u02d7\u02d9\7\u00b8\2\2\u02d8\u02d7"+
		"\3\2\2\2\u02d8\u02d9\3\2\2\2\u02d9\u02da\3\2\2\2\u02da\u0357\5\u00caf"+
		"\2\u02db\u02dd\t\6\2\2\u02dc\u02de\7Z\2\2\u02dd\u02dc\3\2\2\2\u02dd\u02de"+
		"\3\2\2\2\u02de\u02e0\3\2\2\2\u02df\u02e1\t\7\2\2\u02e0\u02df\3\2\2\2\u02e0"+
		"\u02e1\3\2\2\2\u02e1\u02e2\3\2\2\2\u02e2\u02e4\5\u008eH\2\u02e3\u02e5"+
		"\5\"\22\2\u02e4\u02e3\3\2\2\2\u02e4\u02e5\3\2\2\2\u02e5\u02e7\3\2\2\2"+
		"\u02e6\u02e8\5(\25\2\u02e7\u02e6\3\2\2\2\u02e7\u02e8\3\2\2\2\u02e8\u0357"+
		"\3\2\2\2\u02e9\u02ea\7\u00b9\2\2\u02ea\u02eb\7Z\2\2\u02eb\u0357\5\u008e"+
		"H\2\u02ec\u02f4\7\u00b9\2\2\u02ed\u02f5\7\u00fa\2\2\u02ee\u02f0\13\2\2"+
		"\2\u02ef\u02ee\3\2\2\2\u02f0\u02f3\3\2\2\2\u02f1\u02f2\3\2\2\2\u02f1\u02ef"+
		"\3\2\2\2\u02f2\u02f5\3\2\2\2\u02f3\u02f1\3\2\2\2\u02f4\u02ed\3\2\2\2\u02f4"+
		"\u02f1\3\2\2\2\u02f5\u0357\3\2\2\2\u02f6\u02f8\7\u00bb\2\2\u02f7\u02f9"+
		"\7\u00bd\2\2\u02f8\u02f7\3\2\2\2\u02f8\u02f9\3\2\2\2\u02f9\u02fa\3\2\2"+
		"\2\u02fa\u02fb\7Z\2\2\u02fb\u0300\5\u008eH\2\u02fc\u02fe\7\20\2\2\u02fd"+
		"\u02fc\3\2\2\2\u02fd\u02fe\3\2\2\2\u02fe\u02ff\3\2\2\2\u02ff\u0301\5\34"+
		"\17\2\u0300\u02fd\3\2\2\2\u0300\u0301\3\2\2\2\u0301\u0357\3\2\2\2\u0302"+
		"\u0303\7\u00bc\2\2\u0303\u0306\7Z\2\2\u0304\u0305\7\u0089\2\2\u0305\u0307"+
		"\7$\2\2\u0306\u0304\3\2\2\2\u0306\u0307\3\2\2\2\u0307\u0308\3\2\2\2\u0308"+
		"\u0357\5\u008eH\2\u0309\u030a\7\u00ba\2\2\u030a\u0357\7\u00bb\2\2\u030b"+
		"\u030c\7\u00ed\2\2\u030c\u030e\7\177\2\2\u030d\u030f\7\u00f8\2\2\u030e"+
		"\u030d\3\2\2\2\u030e\u030f\3\2\2\2\u030f\u0310\3\2\2\2\u0310\u0311\7\u00f9"+
		"\2\2\u0311\u0313\7\u00fa\2\2\u0312\u0314\7\u00a6\2\2\u0313\u0312\3\2\2"+
		"\2\u0313\u0314\3\2\2\2\u0314\u0315\3\2\2\2\u0315\u0316\7`\2\2\u0316\u0317"+
		"\7Z\2\2\u0317\u0319\5\u008eH\2\u0318\u031a\5\"\22\2\u0319\u0318\3\2\2"+
		"\2\u0319\u031a\3\2\2\2\u031a\u0357\3\2\2\2\u031b\u031c\7\u00dc\2\2\u031c"+
		"\u031d\7Z\2\2\u031d\u031f\5\u008eH\2\u031e\u0320\5\"\22\2\u031f\u031e"+
		"\3\2\2\2\u031f\u0320\3\2\2\2\u0320\u0357\3\2\2\2\u0321\u0322\7\u00e8\2"+
		"\2\u0322\u0323\7\u00e9\2\2\u0323\u0324\7Z\2\2\u0324\u0357\5\u008eH\2\u0325"+
		"\u0326\t\b\2\2\u0326\u032a\5\u00caf\2\u0327\u0329\13\2\2\2\u0328\u0327"+
		"\3\2\2\2\u0329\u032c\3\2\2\2\u032a\u032b\3\2\2\2\u032a\u0328\3\2\2\2\u032b"+
		"\u0357\3\2\2\2\u032c\u032a\3\2\2\2\u032d\u032e\7}\2\2\u032e\u0332\7\u00ee"+
		"\2\2\u032f\u0331\13\2\2\2\u0330\u032f\3\2\2\2\u0331\u0334\3\2\2\2\u0332"+
		"\u0333\3\2\2\2\u0332\u0330\3\2\2\2\u0333\u0357\3\2\2\2\u0334\u0332\3\2"+
		"\2\2\u0335\u0339\7}\2\2\u0336\u0338\13\2\2\2\u0337\u0336\3\2\2\2\u0338"+
		"\u033b\3\2\2\2\u0339\u033a\3\2\2\2\u0339\u0337\3\2\2\2\u033a\u0357\3\2"+
		"\2\2\u033b\u0339\3\2\2\2\u033c\u0357\7~\2\2\u033d\u033e\7Y\2\2\u033e\u033f"+
		"\7\u00f3\2\2\u033f\u0340\5\u00caf\2\u0340\u0341\7?\2\2\u0341\u0342\5l"+
		"\67\2\u0342\u0343\7l\2\2\u0343\u0344\5\20\t\2\u0344\u0357\3\2\2\2\u0345"+
		"\u0346\7h\2\2\u0346\u0347\7\u00f3\2\2\u0347\u0348\7\16\2\2\u0348\u0357"+
		"\5l\67\2\u0349\u034a\7o\2\2\u034a\u034b\7\u00f3\2\2\u034b\u034c\5\u00ca"+
		"f\2\u034c\u034d\7?\2\2\u034d\u034e\5l\67\2\u034e\u0357\3\2\2\2\u034f\u0353"+
		"\5\22\n\2\u0350\u0352\13\2\2\2\u0351\u0350\3\2\2\2\u0352\u0355\3\2\2\2"+
		"\u0353\u0354\3\2\2\2\u0353\u0351\3\2\2\2\u0354\u0357\3\2\2\2\u0355\u0353"+
		"\3\2\2\2\u0356\u00e6\3\2\2\2\u0356\u00e7\3\2\2\2\u0356\u00e9\3\2\2\2\u0356"+
		"\u00fd\3\2\2\2\u0356\u0104\3\2\2\2\u0356\u010e\3\2\2\2\u0356\u012c\3\2"+
		"\2\2\u0356\u014d\3\2\2\2\u0356\u015a\3\2\2\2\u0356\u0168\3\2\2\2\u0356"+
		"\u0171\3\2\2\2\u0356\u0178\3\2\2\2\u0356\u017f\3\2\2\2\u0356\u018a\3\2"+
		"\2\2\u0356\u0199\3\2\2\2\u0356\u01a7\3\2\2\2\u0356\u01b1\3\2\2\2\u0356"+
		"\u01bf\3\2\2\2\u0356\u01cd\3\2\2\2\u0356\u01d5\3\2\2\2\u0356\u01e8\3\2"+
		"\2\2\u0356\u01f8\3\2\2\2\u0356\u0201\3\2\2\2\u0356\u0207\3\2\2\2\u0356"+
		"\u0211\3\2\2\2\u0356\u0218\3\2\2\2\u0356\u023d\3\2\2\2\u0356\u0253\3\2"+
		"\2\2\u0356\u025b\3\2\2\2\u0356\u0277\3\2\2\2\u0356\u0281\3\2\2\2\u0356"+
		"\u0286\3\2\2\2\u0356\u0292\3\2\2\2\u0356\u029e\3\2\2\2\u0356\u02a6\3\2"+
		"\2\2\u0356\u02af\3\2\2\2\u0356\u02b7\3\2\2\2\u0356\u02bd\3\2\2\2\u0356"+
		"\u02cb\3\2\2\2\u0356\u02cf\3\2\2\2\u0356\u02d5\3\2\2\2\u0356\u02db\3\2"+
		"\2\2\u0356\u02e9\3\2\2\2\u0356\u02ec\3\2\2\2\u0356\u02f6\3\2\2\2\u0356"+
		"\u0302\3\2\2\2\u0356\u0309\3\2\2\2\u0356\u030b\3\2\2\2\u0356\u031b\3\2"+
		"\2\2\u0356\u0321\3\2\2\2\u0356\u0325\3\2\2\2\u0356\u032d\3\2\2\2\u0356"+
		"\u0335\3\2\2\2\u0356\u033c\3\2\2\2\u0356\u033d\3\2\2\2\u0356\u0345\3\2"+
		"\2\2\u0356\u0349\3\2\2\2\u0356\u034f\3\2\2\2\u0357\17\3\2\2\2\u0358\u0359"+
		"\t\t\2\2\u0359\21\3\2\2\2\u035a\u035b\7Y\2\2\u035b\u03ff\7\u00ee\2\2\u035c"+
		"\u035d\7o\2\2\u035d\u03ff\7\u00ee\2\2\u035e\u0360\7\u00e5\2\2\u035f\u0361"+
		"\7\u00ee\2\2\u0360\u035f\3\2\2\2\u0360\u0361\3\2\2\2\u0361\u03ff\3\2\2"+
		"\2\u0362\u0364\7\u00e4\2\2\u0363\u0365\7\u00ee\2\2\u0364\u0363\3\2\2\2"+
		"\u0364\u0365\3\2\2\2\u0365\u03ff\3\2\2\2\u0366\u0367\7h\2\2\u0367\u03ff"+
		"\7\u00e5\2\2\u0368\u0369\7h\2\2\u0369\u036b\7\u00ee\2\2\u036a\u036c\7"+
		"\u00e5\2\2\u036b\u036a\3\2\2\2\u036b\u036c\3\2\2\2\u036c\u03ff\3\2\2\2"+
		"\u036d\u036e\7h\2\2\u036e\u03ff\7\u00f1\2\2\u036f\u0370\7h\2\2\u0370\u03ff"+
		"\7\u00ef\2\2\u0371\u0372\7h\2\2\u0372\u0373\7J\2\2\u0373\u03ff\7\u00ef"+
		"\2\2\u0374\u0375\7\u00eb\2\2\u0375\u03ff\7Z\2\2\u0376\u0377\7\u00ec\2"+
		"\2\u0377\u03ff\7Z\2\2\u0378\u0379\7h\2\2\u0379\u03ff\7\u00f0\2\2\u037a"+
		"\u037b\7h\2\2\u037b\u037c\7Y\2\2\u037c\u03ff\7Z\2\2\u037d\u037e\7h\2\2"+
		"\u037e\u03ff\7\u00f2\2\2\u037f\u0380\7h\2\2\u0380\u03ff\7\u00f5\2\2\u0381"+
		"\u0382\7w\2\2\u0382\u03ff\7\u00f3\2\2\u0383\u0384\7\u00e6\2\2\u0384\u03ff"+
		"\7Z\2\2\u0385\u0386\7\u00e6\2\2\u0386\u03ff\7\u00d9\2\2\u0387\u0388\7"+
		"\u00e7\2\2\u0388\u03ff\7Z\2\2\u0389\u038a\7\u00e7\2\2\u038a\u03ff\7\u00d9"+
		"\2\2\u038b\u038c\7Y\2\2\u038c\u038d\7\u00c0\2\2\u038d\u03ff\7\u0084\2"+
		"\2\u038e\u038f\7o\2\2\u038f\u0390\7\u00c0\2\2\u0390\u03ff\7\u0084\2\2"+
		"\u0391\u0392\7w\2\2\u0392\u0393\7Z\2\2\u0393\u0394\5\u008eH\2\u0394\u0395"+
		"\7\"\2\2\u0395\u0396\7\u00d4\2\2\u0396\u03ff\3\2\2\2\u0397\u0398\7w\2"+
		"\2\u0398\u0399\7Z\2\2\u0399\u039a\5\u008eH\2\u039a\u039b\7\u00d4\2\2\u039b"+
		"\u039c\7\26\2\2\u039c\u03ff\3\2\2\2\u039d\u039e\7w\2\2\u039e\u039f\7Z"+
		"\2\2\u039f\u03a0\5\u008eH\2\u03a0\u03a1\7\"\2\2\u03a1\u03a2\7\u00d5\2"+
		"\2\u03a2\u03ff\3\2\2\2\u03a3\u03a4\7w\2\2\u03a4\u03a5\7Z\2\2\u03a5\u03a6"+
		"\5\u008eH\2\u03a6\u03a7\7\u00c6\2\2\u03a7\u03a8\7\26\2\2\u03a8\u03ff\3"+
		"\2\2\2\u03a9\u03aa\7w\2\2\u03aa\u03ab\7Z\2\2\u03ab\u03ac\5\u008eH\2\u03ac"+
		"\u03ad\7\"\2\2\u03ad\u03ae\7\u00c6\2\2\u03ae\u03ff\3\2\2\2\u03af\u03b0"+
		"\7w\2\2\u03b0\u03b1\7Z\2\2\u03b1\u03b2\5\u008eH\2\u03b2\u03b3\7\"\2\2"+
		"\u03b3\u03b4\7\u00c7\2\2\u03b4\u03b5\7\20\2\2\u03b5\u03b6\7\u00c8\2\2"+
		"\u03b6\u03ff\3\2\2\2\u03b7\u03b8\7w\2\2\u03b8\u03b9\7Z\2\2\u03b9\u03ba"+
		"\5\u008eH\2\u03ba\u03bb\7}\2\2\u03bb\u03bc\7\u00c6\2\2\u03bc\u03bd\7\u00c9"+
		"\2\2\u03bd\u03ff\3\2\2\2\u03be\u03bf\7w\2\2\u03bf\u03c0\7Z\2\2\u03c0\u03c1"+
		"\5\u008eH\2\u03c1\u03c2\7\u00ca\2\2\u03c2\u03c3\7D\2\2\u03c3\u03ff\3\2"+
		"\2\2\u03c4\u03c5\7w\2\2\u03c5\u03c6\7Z\2\2\u03c6\u03c7\5\u008eH\2\u03c7"+
		"\u03c8\7\u00cb\2\2\u03c8\u03c9\7D\2\2\u03c9\u03ff\3\2\2\2\u03ca\u03cb"+
		"\7w\2\2\u03cb\u03cc\7Z\2\2\u03cc\u03cd\5\u008eH\2\u03cd\u03ce\7\u00cc"+
		"\2\2\u03ce\u03cf\7D\2\2\u03cf\u03ff\3\2\2\2\u03d0\u03d1\7w\2\2\u03d1\u03d2"+
		"\7Z\2\2\u03d2\u03d3\5\u008eH\2\u03d3\u03d4\7\u00ce\2\2\u03d4\u03ff\3\2"+
		"\2\2\u03d5\u03d6\7w\2\2\u03d6\u03d7\7Z\2\2\u03d7\u03d9\5\u008eH\2\u03d8"+
		"\u03da\5\"\22\2\u03d9\u03d8\3\2\2\2\u03d9\u03da\3\2\2\2\u03da\u03db\3"+
		"\2\2\2\u03db\u03dc\7\u00cf\2\2\u03dc\u03ff\3\2\2\2\u03dd\u03de\7w\2\2"+
		"\u03de\u03df\7Z\2\2\u03df\u03e1\5\u008eH\2\u03e0\u03e2\5\"\22\2\u03e1"+
		"\u03e0\3\2\2\2\u03e1\u03e2\3\2\2\2\u03e2\u03e3\3\2\2\2\u03e3\u03e4\7\u00d0"+
		"\2\2\u03e4\u03ff\3\2\2\2\u03e5\u03e6\7w\2\2\u03e6\u03e7\7Z\2\2\u03e7\u03e9"+
		"\5\u008eH\2\u03e8\u03ea\5\"\22\2\u03e9\u03e8\3\2\2\2\u03e9\u03ea\3\2\2"+
		"\2\u03ea\u03eb\3\2\2\2\u03eb\u03ec\7}\2\2\u03ec\u03ed\7\u00cd\2\2\u03ed"+
		"\u03ff\3\2\2\2\u03ee\u03ef\7w\2\2\u03ef\u03f0\7Z\2\2\u03f0\u03f2\5\u008e"+
		"H\2\u03f1\u03f3\5\"\22\2\u03f2\u03f1\3\2\2\2\u03f2\u03f3\3\2\2\2\u03f3"+
		"\u03f4\3\2\2\2\u03f4\u03f5\7]\2\2\u03f5\u03f6\7j\2\2\u03f6\u03ff\3\2\2"+
		"\2\u03f7\u03f8\7\u0080\2\2\u03f8\u03ff\7\u0081\2\2\u03f9\u03ff\7\u0082"+
		"\2\2\u03fa\u03ff\7\u0083\2\2\u03fb\u03ff\7\u00db\2\2\u03fc\u03fd\7_\2"+
		"\2\u03fd\u03ff\7\16\2\2\u03fe\u035a\3\2\2\2\u03fe\u035c\3\2\2\2\u03fe"+
		"\u035e\3\2\2\2\u03fe\u0362\3\2\2\2\u03fe\u0366\3\2\2\2\u03fe\u0368\3\2"+
		"\2\2\u03fe\u036d\3\2\2\2\u03fe\u036f\3\2\2\2\u03fe\u0371\3\2\2\2\u03fe"+
		"\u0374\3\2\2\2\u03fe\u0376\3\2\2\2\u03fe\u0378\3\2\2\2\u03fe\u037a\3\2"+
		"\2\2\u03fe\u037d\3\2\2\2\u03fe\u037f\3\2\2\2\u03fe\u0381\3\2\2\2\u03fe"+
		"\u0383\3\2\2\2\u03fe\u0385\3\2\2\2\u03fe\u0387\3\2\2\2\u03fe\u0389\3\2"+
		"\2\2\u03fe\u038b\3\2\2\2\u03fe\u038e\3\2\2\2\u03fe\u0391\3\2\2\2\u03fe"+
		"\u0397\3\2\2\2\u03fe\u039d\3\2\2\2\u03fe\u03a3\3\2\2\2\u03fe\u03a9\3\2"+
		"\2\2\u03fe\u03af\3\2\2\2\u03fe\u03b7\3\2\2\2\u03fe\u03be\3\2\2\2\u03fe"+
		"\u03c4\3\2\2\2\u03fe\u03ca\3\2\2\2\u03fe\u03d0\3\2\2\2\u03fe\u03d5\3\2"+
		"\2\2\u03fe\u03dd\3\2\2\2\u03fe\u03e5\3\2\2\2\u03fe\u03ee\3\2\2\2\u03fe"+
		"\u03f7\3\2\2\2\u03fe\u03f9\3\2\2\2\u03fe\u03fa\3\2\2\2\u03fe\u03fb\3\2"+
		"\2\2\u03fe\u03fc\3\2\2\2\u03ff\23\3\2\2\2\u0400\u0402\7Y\2\2\u0401\u0403"+
		"\7\u00c0\2\2\u0402\u0401\3\2\2\2\u0402\u0403\3\2\2\2\u0403\u0405\3\2\2"+
		"\2\u0404\u0406\7\u00e2\2\2\u0405\u0404\3\2\2\2\u0405\u0406\3\2\2\2\u0406"+
		"\u0407\3\2\2\2\u0407\u040b\7Z\2\2\u0408\u0409\7\u0089\2\2\u0409\u040a"+
		"\7\"\2\2\u040a\u040c\7$\2\2\u040b\u0408\3\2\2\2\u040b\u040c\3\2\2\2\u040c"+
		"\u040d\3\2\2\2\u040d\u040e\5\u008eH\2\u040e\25\3\2\2\2\u040f\u0410\7\u00d4"+
		"\2\2\u0410\u0411\7\26\2\2\u0411\u0415\5x=\2\u0412\u0413\7\u00d5\2\2\u0413"+
		"\u0414\7\26\2\2\u0414\u0416\5|?\2\u0415\u0412\3\2\2\2\u0415\u0416\3\2"+
		"\2\2\u0416\u0417\3\2\2\2\u0417\u0418\7`\2\2\u0418\u0419\7\u00fe\2\2\u0419"+
		"\u041a\7\u00c5\2\2\u041a\27\3\2\2\2\u041b\u041c\7\u00c6\2\2\u041c\u041d"+
		"\7\26\2\2\u041d\u041e\5x=\2\u041e\u0421\7?\2\2\u041f\u0422\58\35\2\u0420"+
		"\u0422\5:\36\2\u0421\u041f\3\2\2\2\u0421\u0420\3\2\2\2\u0422\u0426\3\2"+
		"\2\2\u0423\u0424\7\u00c7\2\2\u0424\u0425\7\20\2\2\u0425\u0427\7\u00c8"+
		"\2\2\u0426\u0423\3\2\2\2\u0426\u0427\3\2\2\2\u0427\31\3\2\2\2\u0428\u0429"+
		"\7\u00c9\2\2\u0429\u042a\7\u00fa\2\2\u042a\33\3\2\2\2\u042b\u042d\5*\26"+
		"\2\u042c\u042b\3\2\2\2\u042c\u042d\3\2\2\2\u042d\u042e\3\2\2\2\u042e\u042f"+
		"\5D#\2\u042f\35\3\2\2\2\u0430\u0431\7^\2\2\u0431\u0432\7\u00a6\2\2\u0432"+
		"\u0433\7Z\2\2\u0433\u043a\5\u008eH\2\u0434\u0438\5\"\22\2\u0435\u0436"+
		"\7\u0089\2\2\u0436\u0437\7\"\2\2\u0437\u0439\7$\2\2\u0438\u0435\3\2\2"+
		"\2\u0438\u0439\3\2\2\2\u0439\u043b\3\2\2\2\u043a\u0434\3\2\2\2\u043a\u043b"+
		"\3\2\2\2\u043b\u0461\3\2\2\2\u043c\u043d\7^\2\2\u043d\u043f\7`\2\2\u043e"+
		"\u0440\7Z\2\2\u043f\u043e\3\2\2\2\u043f\u0440\3\2\2\2\u0440\u0441\3\2"+
		"\2\2\u0441\u0443\5\u008eH\2\u0442\u0444\5\"\22\2\u0443\u0442\3\2\2\2\u0443"+
		"\u0444\3\2\2\2\u0444\u0461\3\2\2\2\u0445\u0446\7^\2\2\u0446\u0448\7\u00a6"+
		"\2\2\u0447\u0449\7\u00f8\2\2\u0448\u0447\3\2\2\2\u0448\u0449\3\2\2\2\u0449"+
		"\u044a\3\2\2\2\u044a\u044b\7[\2\2\u044b\u044d\7\u00fa\2\2\u044c\u044e"+
		"\5\u008cG\2\u044d\u044c\3\2\2\2\u044d\u044e\3\2\2\2\u044e\u0450\3\2\2"+
		"\2\u044f\u0451\5<\37\2\u0450\u044f\3\2\2\2\u0450\u0451\3\2\2\2\u0451\u0461"+
		"\3\2\2\2\u0452\u0453\7^\2\2\u0453\u0455\7\u00a6\2\2\u0454\u0456\7\u00f8"+
		"\2\2\u0455\u0454\3\2\2\2\u0455\u0456\3\2\2\2\u0456\u0457\3\2\2\2\u0457"+
		"\u0459\7[\2\2\u0458\u045a\7\u00fa\2\2\u0459\u0458\3\2\2\2\u0459\u045a"+
		"\3\2\2\2\u045a\u045b\3\2\2\2\u045b\u045e\5.\30\2\u045c\u045d\7\u00c1\2"+
		"\2\u045d\u045f\5\60\31\2\u045e\u045c\3\2\2\2\u045e\u045f\3\2\2\2\u045f"+
		"\u0461\3\2\2\2\u0460\u0430\3\2\2\2\u0460\u043c\3\2\2\2\u0460\u0445\3\2"+
		"\2\2\u0460\u0452\3\2\2\2\u0461\37\3\2\2\2\u0462\u0464\5\"\22\2\u0463\u0465"+
		"\5\32\16\2\u0464\u0463\3\2\2\2\u0464\u0465\3\2\2\2\u0465!\3\2\2\2\u0466"+
		"\u0467\7D\2\2\u0467\u0468\7\3\2\2\u0468\u046d\5$\23\2\u0469\u046a\7\5"+
		"\2\2\u046a\u046c\5$\23\2\u046b\u0469\3\2\2\2\u046c\u046f\3\2\2\2\u046d"+
		"\u046b\3\2\2\2\u046d\u046e\3\2\2\2\u046e\u0470\3\2\2\2\u046f\u046d\3\2"+
		"\2\2\u0470\u0471\7\4\2\2\u0471#\3\2\2\2\u0472\u0475\5\u00caf\2\u0473\u0474"+
		"\7\u008c\2\2\u0474\u0476\5\u00a0Q\2\u0475\u0473\3\2\2\2\u0475\u0476\3"+
		"\2\2\2\u0476%\3\2\2\2\u0477\u047d\5\u00c8e\2\u0478\u047d\7\u00fa\2\2\u0479"+
		"\u047d\5\u00a2R\2\u047a\u047d\5\u00a4S\2\u047b\u047d\5\u00a6T\2\u047c"+
		"\u0477\3\2\2\2\u047c\u0478\3\2\2\2\u047c\u0479\3\2\2\2\u047c\u047a\3\2"+
		"\2\2\u047c\u047b\3\2\2\2\u047d\'\3\2\2\2\u047e\u0483\5\u00caf\2\u047f"+
		"\u0480\7\6\2\2\u0480\u0482\5\u00caf\2\u0481\u047f\3\2\2\2\u0482\u0485"+
		"\3\2\2\2\u0483\u0481\3\2\2\2\u0483\u0484\3\2\2\2\u0484)\3\2\2\2\u0485"+
		"\u0483\3\2\2\2\u0486\u0487\7O\2\2\u0487\u048c\5,\27\2\u0488\u0489\7\5"+
		"\2\2\u0489\u048b\5,\27\2\u048a\u0488\3\2\2\2\u048b\u048e\3\2\2\2\u048c"+
		"\u048a\3\2\2\2\u048c\u048d\3\2\2\2\u048d+\3\2\2\2\u048e\u048c\3\2\2\2"+
		"\u048f\u0491\5\u00caf\2\u0490\u0492\7\20\2\2\u0491\u0490\3\2\2\2\u0491"+
		"\u0492\3\2\2\2\u0492\u0493\3\2\2\2\u0493\u0494\7\3\2\2\u0494\u0495\5\34"+
		"\17\2\u0495\u0496\7\4\2\2\u0496-\3\2\2\2\u0497\u0498\7\u00a9\2\2\u0498"+
		"\u0499\5\u00c8e\2\u0499/\3\2\2\2\u049a\u049b\7\3\2\2\u049b\u04a0\5\62"+
		"\32\2\u049c\u049d\7\5\2\2\u049d\u049f\5\62\32\2\u049e\u049c\3\2\2\2\u049f"+
		"\u04a2\3\2\2\2\u04a0\u049e\3\2\2\2\u04a0\u04a1\3\2\2\2\u04a1\u04a3\3\2"+
		"\2\2\u04a2\u04a0\3\2\2\2\u04a3\u04a4\7\4\2\2\u04a4\61\3\2\2\2\u04a5\u04aa"+
		"\5\64\33\2\u04a6\u04a8\7\u008c\2\2\u04a7\u04a6\3\2\2\2\u04a7\u04a8\3\2"+
		"\2\2\u04a8\u04a9\3\2\2\2\u04a9\u04ab\5\66\34\2\u04aa\u04a7\3\2\2\2\u04aa"+
		"\u04ab\3\2\2\2\u04ab\63\3\2\2\2\u04ac\u04b1\5\u00caf\2\u04ad\u04ae\7\6"+
		"\2\2\u04ae\u04b0\5\u00caf\2\u04af\u04ad\3\2\2\2\u04b0\u04b3\3\2\2\2\u04b1"+
		"\u04af\3\2\2\2\u04b1\u04b2\3\2\2\2\u04b2\u04b6\3\2\2\2\u04b3\u04b1\3\2"+
		"\2\2\u04b4\u04b6\7\u00fa\2\2\u04b5\u04ac\3\2\2\2\u04b5\u04b4\3\2\2\2\u04b6"+
		"\65\3\2\2\2\u04b7\u04bc\7\u00fe\2\2\u04b8\u04bc\7\u00ff\2\2\u04b9\u04bc"+
		"\5\u00a8U\2\u04ba\u04bc\7\u00fa\2\2\u04bb\u04b7\3\2\2\2\u04bb\u04b8\3"+
		"\2\2\2\u04bb\u04b9\3\2\2\2\u04bb\u04ba\3\2\2\2\u04bc\67\3\2\2\2\u04bd"+
		"\u04be\7\3\2\2\u04be\u04c3\5\u00a0Q\2\u04bf\u04c0\7\5\2\2\u04c0\u04c2"+
		"\5\u00a0Q\2\u04c1\u04bf\3\2\2\2\u04c2\u04c5\3\2\2\2\u04c3\u04c1\3\2\2"+
		"\2\u04c3\u04c4\3\2\2\2\u04c4\u04c6\3\2\2\2\u04c5\u04c3\3\2\2\2\u04c6\u04c7"+
		"\7\4\2\2\u04c79\3\2\2\2\u04c8\u04c9\7\3\2\2\u04c9\u04ce\58\35\2\u04ca"+
		"\u04cb\7\5\2\2\u04cb\u04cd\58\35\2\u04cc\u04ca\3\2\2\2\u04cd\u04d0\3\2"+
		"\2\2\u04ce\u04cc\3\2\2\2\u04ce\u04cf\3\2\2\2\u04cf\u04d1\3\2\2\2\u04d0"+
		"\u04ce\3\2\2\2\u04d1\u04d2\7\4\2\2\u04d2;\3\2\2\2\u04d3\u04d4\7\u00c7"+
		"\2\2\u04d4\u04d5\7\20\2\2\u04d5\u04da\5> \2\u04d6\u04d7\7\u00c7\2\2\u04d7"+
		"\u04d8\7\26\2\2\u04d8\u04da\5@!\2\u04d9\u04d3\3\2\2\2\u04d9\u04d6\3\2"+
		"\2\2\u04da=\3\2\2\2\u04db\u04dc\7\u00d7\2\2\u04dc\u04dd\7\u00fa\2\2\u04dd"+
		"\u04de\7\u00d8\2\2\u04de\u04e1\7\u00fa\2\2\u04df\u04e1\5\u00caf\2\u04e0"+
		"\u04db\3\2\2\2\u04e0\u04df\3\2\2\2\u04e1?\3\2\2\2\u04e2\u04e6\7\u00fa"+
		"\2\2\u04e3\u04e4\7O\2\2\u04e4\u04e5\7\u00ab\2\2\u04e5\u04e7\5\60\31\2"+
		"\u04e6\u04e3\3\2\2\2\u04e6\u04e7\3\2\2\2\u04e7A\3\2\2\2\u04e8\u04e9\5"+
		"\u00caf\2\u04e9\u04ea\7\u00fa\2\2\u04eaC\3\2\2\2\u04eb\u04ed\5\36\20\2"+
		"\u04ec\u04eb\3\2\2\2\u04ec\u04ed\3\2\2\2\u04ed\u04ee\3\2\2\2\u04ee\u04ef"+
		"\5J&\2\u04ef\u04f0\5F$\2\u04f0\u04f8\3\2\2\2\u04f1\u04f3\5\\/\2\u04f2"+
		"\u04f4\5H%\2\u04f3\u04f2\3\2\2\2\u04f4\u04f5\3\2\2\2\u04f5\u04f3\3\2\2"+
		"\2\u04f5\u04f6\3\2\2\2\u04f6\u04f8\3\2\2\2\u04f7\u04ec\3\2\2\2\u04f7\u04f1"+
		"\3\2\2\2\u04f8E\3\2\2\2\u04f9\u04fa\7\33\2\2\u04fa\u04fb\7\26\2\2\u04fb"+
		"\u0500\5N(\2\u04fc\u04fd\7\5\2\2\u04fd\u04ff\5N(\2\u04fe\u04fc\3\2\2\2"+
		"\u04ff\u0502\3\2\2\2\u0500\u04fe\3\2\2\2\u0500\u0501\3\2\2\2\u0501\u0504"+
		"\3\2\2\2\u0502\u0500\3\2\2\2\u0503\u04f9\3\2\2\2\u0503\u0504\3\2\2\2\u0504"+
		"\u050f\3\2\2\2\u0505\u0506\7\u00a4\2\2\u0506\u0507\7\26\2\2\u0507\u050c"+
		"\5\u0096L\2\u0508\u0509\7\5\2\2\u0509\u050b\5\u0096L\2\u050a\u0508\3\2"+
		"\2\2\u050b\u050e\3\2\2\2\u050c\u050a\3\2\2\2\u050c\u050d\3\2\2\2\u050d"+
		"\u0510\3\2\2\2\u050e\u050c\3\2\2\2\u050f\u0505\3\2\2\2\u050f\u0510\3\2"+
		"\2\2\u0510\u051b\3\2\2\2\u0511\u0512\7\u00a5\2\2\u0512\u0513\7\26\2\2"+
		"\u0513\u0518\5\u0096L\2\u0514\u0515\7\5\2\2\u0515\u0517\5\u0096L\2\u0516"+
		"\u0514\3\2\2\2\u0517\u051a\3\2\2\2\u0518\u0516\3\2\2\2\u0518\u0519\3\2"+
		"\2\2\u0519\u051c\3\2\2\2\u051a\u0518\3\2\2\2\u051b\u0511\3\2\2\2\u051b"+
		"\u051c\3\2\2\2\u051c\u0527\3\2\2\2\u051d\u051e\7\u00a3\2\2\u051e\u051f"+
		"\7\26\2\2\u051f\u0524\5N(\2\u0520\u0521\7\5\2\2\u0521\u0523\5N(\2\u0522"+
		"\u0520\3\2\2\2\u0523\u0526\3\2\2\2\u0524\u0522\3\2\2\2\u0524\u0525\3\2"+
		"\2\2\u0525\u0528\3\2\2\2\u0526\u0524\3\2\2\2\u0527\u051d\3\2\2\2\u0527"+
		"\u0528\3\2\2\2\u0528\u052a\3\2\2\2\u0529\u052b\5\u00be`\2\u052a\u0529"+
		"\3\2\2\2\u052a\u052b\3\2\2\2\u052b\u0531\3\2\2\2\u052c\u052f\7\35\2\2"+
		"\u052d\u0530\7\21\2\2\u052e\u0530\5\u0096L\2\u052f\u052d\3\2\2\2\u052f"+
		"\u052e\3\2\2\2\u0530\u0532\3\2\2\2\u0531\u052c\3\2\2\2\u0531\u0532\3\2"+
		"\2\2\u0532G\3\2\2\2\u0533\u0535\5\36\20\2\u0534\u0533\3\2\2\2\u0534\u0535"+
		"\3\2\2\2\u0535\u0536\3\2\2\2\u0536\u0537\5P)\2\u0537\u0538\5F$\2\u0538"+
		"I\3\2\2\2\u0539\u053a\b&\1\2\u053a\u053b\5L\'\2\u053b\u0553\3\2\2\2\u053c"+
		"\u053d\f\5\2\2\u053d\u053e\6&\3\2\u053e\u0540\t\n\2\2\u053f\u0541\5j\66"+
		"\2\u0540\u053f\3\2\2\2\u0540\u0541\3\2\2\2\u0541\u0542\3\2\2\2\u0542\u0552"+
		"\5J&\6\u0543\u0544\f\4\2\2\u0544\u0545\6&\5\2\u0545\u0547\7s\2\2\u0546"+
		"\u0548\5j\66\2\u0547\u0546\3\2\2\2\u0547\u0548\3\2\2\2\u0548\u0549\3\2"+
		"\2\2\u0549\u0552\5J&\5\u054a\u054b\f\3\2\2\u054b\u054c\6&\7\2\u054c\u054e"+
		"\t\13\2\2\u054d\u054f\5j\66\2\u054e\u054d\3\2\2\2\u054e\u054f\3\2\2\2"+
		"\u054f\u0550\3\2\2\2\u0550\u0552\5J&\4\u0551\u053c\3\2\2\2\u0551\u0543"+
		"\3\2\2\2\u0551\u054a\3\2\2\2\u0552\u0555\3\2\2\2\u0553\u0551\3\2\2\2\u0553"+
		"\u0554\3\2\2\2\u0554K\3\2\2\2\u0555\u0553\3\2\2\2\u0556\u055f\5P)\2\u0557"+
		"\u0558\7Z\2\2\u0558\u055f\5\u008eH\2\u0559\u055f\5\u0086D\2\u055a\u055b"+
		"\7\3\2\2\u055b\u055c\5D#\2\u055c\u055d\7\4\2\2\u055d\u055f\3\2\2\2\u055e"+
		"\u0556\3\2\2\2\u055e\u0557\3\2\2\2\u055e\u0559\3\2\2\2\u055e\u055a\3\2"+
		"\2\2\u055fM\3\2\2\2\u0560\u0562\5\u0096L\2\u0561\u0563\t\f\2\2\u0562\u0561"+
		"\3\2\2\2\u0562\u0563\3\2\2\2\u0563\u0566\3\2\2\2\u0564\u0565\7,\2\2\u0565"+
		"\u0567\t\r\2\2\u0566\u0564\3\2\2\2\u0566\u0567\3\2\2\2\u0567O\3\2\2\2"+
		"\u0568\u0569\7\r\2\2\u0569\u056a\7\u00a7\2\2\u056a\u056b\7\3\2\2\u056b"+
		"\u056c\5\u0094K\2\u056c\u056d\7\4\2\2\u056d\u0573\3\2\2\2\u056e\u056f"+
		"\7z\2\2\u056f\u0573\5\u0094K\2\u0570\u0571\7\u00a8\2\2\u0571\u0573\5\u0094"+
		"K\2\u0572\u0568\3\2\2\2\u0572\u056e\3\2\2\2\u0572\u0570\3\2\2\2\u0573"+
		"\u0575\3\2\2\2\u0574\u0576\5\u008cG\2\u0575\u0574\3\2\2\2\u0575\u0576"+
		"\3\2\2\2\u0576\u0579\3\2\2\2\u0577\u0578\7\u00ad\2\2\u0578\u057a\7\u00fa"+
		"\2\2\u0579\u0577\3\2\2\2\u0579\u057a\3\2\2\2\u057a\u057b\3\2\2\2\u057b"+
		"\u057c\7\u00a9\2\2\u057c\u0589\7\u00fa\2\2\u057d\u0587\7\20\2\2\u057e"+
		"\u0588\5z>\2\u057f\u0588\5\u00b4[\2\u0580\u0583\7\3\2\2\u0581\u0584\5"+
		"z>\2\u0582\u0584\5\u00b4[\2\u0583\u0581\3\2\2\2\u0583\u0582\3\2\2\2\u0584"+
		"\u0585\3\2\2\2\u0585\u0586\7\4\2\2\u0586\u0588\3\2\2\2\u0587\u057e\3\2"+
		"\2\2\u0587\u057f\3\2\2\2\u0587\u0580\3\2\2\2\u0588\u058a\3\2\2\2\u0589"+
		"\u057d\3\2\2\2\u0589\u058a\3\2\2\2\u058a\u058c\3\2\2\2\u058b\u058d\5\u008c"+
		"G\2\u058c\u058b\3\2\2\2\u058c\u058d\3\2\2\2\u058d\u0590\3\2\2\2\u058e"+
		"\u058f\7\u00ac\2\2\u058f\u0591\7\u00fa\2\2\u0590\u058e\3\2\2\2\u0590\u0591"+
		"\3\2\2\2\u0591\u0593\3\2\2\2\u0592\u0594\5\\/\2\u0593\u0592\3\2\2\2\u0593"+
		"\u0594\3\2\2\2\u0594\u0597\3\2\2\2\u0595\u0596\7\24\2\2\u0596\u0598\5"+
		"\u0098M\2\u0597\u0595\3\2\2\2\u0597\u0598\3\2\2\2\u0598\u05c9\3\2\2\2"+
		"\u0599\u059d\7\r\2\2\u059a\u059c\5X-\2\u059b\u059a\3\2\2\2\u059c\u059f"+
		"\3\2\2\2\u059d\u059b\3\2\2\2\u059d\u059e\3\2\2\2\u059e\u05a1\3\2\2\2\u059f"+
		"\u059d\3\2\2\2\u05a0\u05a2\5j\66\2\u05a1\u05a0\3\2\2\2\u05a1\u05a2\3\2"+
		"\2\2\u05a2\u05a3\3\2\2\2\u05a3\u05a5\5\u0094K\2\u05a4\u05a6\5\\/\2\u05a5"+
		"\u05a4\3\2\2\2\u05a5\u05a6\3\2\2\2\u05a6\u05b0\3\2\2\2\u05a7\u05ad\5\\"+
		"/\2\u05a8\u05aa\7\r\2\2\u05a9\u05ab\5j\66\2\u05aa\u05a9\3\2\2\2\u05aa"+
		"\u05ab\3\2\2\2\u05ab\u05ac\3\2\2\2\u05ac\u05ae\5\u0094K\2\u05ad\u05a8"+
		"\3\2\2\2\u05ad\u05ae\3\2\2\2\u05ae\u05b0\3\2\2\2\u05af\u0599\3\2\2\2\u05af"+
		"\u05a7\3\2\2\2\u05b0\u05b4\3\2\2\2\u05b1\u05b3\5h\65\2\u05b2\u05b1\3\2"+
		"\2\2\u05b3\u05b6\3\2\2\2\u05b4\u05b2\3\2\2\2\u05b4\u05b5\3\2\2\2\u05b5"+
		"\u05b9\3\2\2\2\u05b6\u05b4\3\2\2\2\u05b7\u05b8\7\24\2\2\u05b8\u05ba\5"+
		"\u0098M\2\u05b9\u05b7\3\2\2\2\u05b9\u05ba\3\2\2\2\u05ba\u05bc\3\2\2\2"+
		"\u05bb\u05bd\5^\60\2\u05bc\u05bb\3\2\2\2\u05bc\u05bd\3\2\2\2\u05bd\u05c0"+
		"\3\2\2\2\u05be\u05bf\7\34\2\2\u05bf\u05c1\5\u0098M\2\u05c0\u05be\3\2\2"+
		"\2\u05c0\u05c1\3\2\2\2\u05c1\u05c3\3\2\2\2\u05c2\u05c4\5\u00be`\2\u05c3"+
		"\u05c2\3\2\2\2\u05c3\u05c4\3\2\2\2\u05c4\u05c6\3\2\2\2\u05c5\u05c7\5R"+
		"*\2\u05c6\u05c5\3\2\2\2\u05c6\u05c7\3\2\2\2\u05c7\u05c9\3\2\2\2\u05c8"+
		"\u0572\3\2\2\2\u05c8\u05af\3\2\2\2\u05c9Q\3\2\2\2\u05ca\u05cb\5T+\2\u05cb"+
		"\u05cc\5V,\2\u05ccS\3\2\2\2\u05cd\u05ce\7P\2\2\u05ce\u05cf\7Q\2\2\u05cf"+
		"\u05d0\7\u0102\2\2\u05d0U\3\2\2\2\u05d1\u05d2\7\36\2\2\u05d2\u05d3\7R"+
		"\2\2\u05d3\u05d4\7\u0102\2\2\u05d4W\3\2\2\2\u05d5\u05d6\7\7\2\2\u05d6"+
		"\u05dd\5Z.\2\u05d7\u05d9\7\5\2\2\u05d8\u05d7\3\2\2\2\u05d8\u05d9\3\2\2"+
		"\2\u05d9\u05da\3\2\2\2\u05da\u05dc\5Z.\2\u05db\u05d8\3\2\2\2\u05dc\u05df"+
		"\3\2\2\2\u05dd\u05db\3\2\2\2\u05dd\u05de\3\2\2\2\u05de\u05e0\3\2\2\2\u05df"+
		"\u05dd\3\2\2\2\u05e0\u05e1\7\b\2\2\u05e1Y\3\2\2\2\u05e2\u05f0\5\u00ca"+
		"f\2\u05e3\u05e4\5\u00caf\2\u05e4\u05e5\7\3\2\2\u05e5\u05ea\5\u009eP\2"+
		"\u05e6\u05e7\7\5\2\2\u05e7\u05e9\5\u009eP\2\u05e8\u05e6\3\2\2\2\u05e9"+
		"\u05ec\3\2\2\2\u05ea\u05e8\3\2\2\2\u05ea\u05eb\3\2\2\2\u05eb\u05ed\3\2"+
		"\2\2\u05ec\u05ea\3\2\2\2\u05ed\u05ee\7\4\2\2\u05ee\u05f0\3\2\2\2\u05ef"+
		"\u05e2\3\2\2\2\u05ef\u05e3\3\2\2\2\u05f0[\3\2\2\2\u05f1\u05f2\7\16\2\2"+
		"\u05f2\u05f7\5l\67\2\u05f3\u05f4\7\5\2\2\u05f4\u05f6\5l\67\2\u05f5\u05f3"+
		"\3\2\2\2\u05f6\u05f9\3\2\2\2\u05f7\u05f5\3\2\2\2\u05f7\u05f8\3\2\2\2\u05f8"+
		"\u05fd\3\2\2\2\u05f9\u05f7\3\2\2\2\u05fa\u05fc\5h\65\2\u05fb\u05fa\3\2"+
		"\2\2\u05fc\u05ff\3\2\2\2\u05fd\u05fb\3\2\2\2\u05fd\u05fe\3\2\2\2\u05fe"+
		"\u0601\3\2\2\2\u05ff\u05fd\3\2\2\2\u0600\u0602\5b\62\2\u0601\u0600\3\2"+
		"\2\2\u0601\u0602\3\2\2\2\u0602]\3\2\2\2\u0603\u0604\7\25\2\2\u0604\u0605"+
		"\7\26\2\2\u0605\u060a\5\u0096L\2\u0606\u0607\7\5\2\2\u0607\u0609\5\u0096"+
		"L\2\u0608\u0606\3\2\2\2\u0609\u060c\3\2\2\2\u060a\u0608\3\2\2\2\u060a"+
		"\u060b\3\2\2\2\u060b\u061e\3\2\2\2\u060c\u060a\3\2\2\2\u060d\u060e\7O"+
		"\2\2\u060e\u061f\7\32\2\2\u060f\u0610\7O\2\2\u0610\u061f\7\31\2\2\u0611"+
		"\u0612\7\27\2\2\u0612\u0613\7\30\2\2\u0613\u0614\7\3\2\2\u0614\u0619\5"+
		"`\61\2\u0615\u0616\7\5\2\2\u0616\u0618\5`\61\2\u0617\u0615\3\2\2\2\u0618"+
		"\u061b\3\2\2\2\u0619\u0617\3\2\2\2\u0619\u061a\3\2\2\2\u061a\u061c\3\2"+
		"\2\2\u061b\u0619\3\2\2\2\u061c\u061d\7\4\2\2\u061d\u061f\3\2\2\2\u061e"+
		"\u060d\3\2\2\2\u061e\u060f\3\2\2\2\u061e\u0611\3\2\2\2\u061e\u061f\3\2"+
		"\2\2\u061f\u0630\3\2\2\2\u0620\u0621\7\25\2\2\u0621\u0622\7\26\2\2\u0622"+
		"\u0623\7\27\2\2\u0623\u0624\7\30\2\2\u0624\u0625\7\3\2\2\u0625\u062a\5"+
		"`\61\2\u0626\u0627\7\5\2\2\u0627\u0629\5`\61\2\u0628\u0626\3\2\2\2\u0629"+
		"\u062c\3\2\2\2\u062a\u0628\3\2\2\2\u062a\u062b\3\2\2\2\u062b\u062d\3\2"+
		"\2\2\u062c\u062a\3\2\2\2\u062d\u062e\7\4\2\2\u062e\u0630\3\2\2\2\u062f"+
		"\u0603\3\2\2\2\u062f\u0620\3\2\2\2\u0630_\3\2\2\2\u0631\u063a\7\3\2\2"+
		"\u0632\u0637\5\u0096L\2\u0633\u0634\7\5\2\2\u0634\u0636\5\u0096L\2\u0635"+
		"\u0633\3\2\2\2\u0636\u0639\3\2\2\2\u0637\u0635\3\2\2\2\u0637\u0638\3\2"+
		"\2\2\u0638\u063b\3\2\2\2\u0639\u0637\3\2\2\2\u063a\u0632\3\2\2\2\u063a"+
		"\u063b\3\2\2\2\u063b\u063c\3\2\2\2\u063c\u063f\7\4\2\2\u063d\u063f\5\u0096"+
		"L\2\u063e\u0631\3\2\2\2\u063e\u063d\3\2\2\2\u063fa\3\2\2\2\u0640\u0641"+
		"\7@\2\2\u0641\u0642\7\3\2\2\u0642\u0643\5\u0094K\2\u0643\u0644\7/\2\2"+
		"\u0644\u0645\5d\63\2\u0645\u0646\7!\2\2\u0646\u0647\7\3\2\2\u0647\u064c"+
		"\5f\64\2\u0648\u0649\7\5\2\2\u0649\u064b\5f\64\2\u064a\u0648\3\2\2\2\u064b"+
		"\u064e\3\2\2\2\u064c\u064a\3\2\2\2\u064c\u064d\3\2\2\2\u064d\u064f\3\2"+
		"\2\2\u064e\u064c\3\2\2\2\u064f\u0650\7\4\2\2\u0650\u0651\7\4\2\2\u0651"+
		"c\3\2\2\2\u0652\u065f\5\u00caf\2\u0653\u0654\7\3\2\2\u0654\u0659\5\u00ca"+
		"f\2\u0655\u0656\7\5\2\2\u0656\u0658\5\u00caf\2\u0657\u0655\3\2\2\2\u0658"+
		"\u065b\3\2\2\2\u0659\u0657\3\2\2\2\u0659\u065a\3\2\2\2\u065a\u065c\3\2"+
		"\2\2\u065b\u0659\3\2\2\2\u065c\u065d\7\4\2\2\u065d\u065f\3\2\2\2\u065e"+
		"\u0652\3\2\2\2\u065e\u0653\3\2\2\2\u065fe\3\2\2\2\u0660\u0665\5\u0096"+
		"L\2\u0661\u0663\7\20\2\2\u0662\u0661\3\2\2\2\u0662\u0663\3\2\2\2\u0663"+
		"\u0664\3\2\2\2\u0664\u0666\5\u00caf\2\u0665\u0662\3\2\2\2\u0665\u0666"+
		"\3\2\2\2\u0666g\3\2\2\2\u0667\u0668\7A\2\2\u0668\u066a\7\\\2\2\u0669\u066b"+
		"\78\2\2\u066a\u0669\3\2\2\2\u066a\u066b\3\2\2\2\u066b\u066c\3\2\2\2\u066c"+
		"\u066d\5\u00c8e\2\u066d\u0676\7\3\2\2\u066e\u0673\5\u0096L\2\u066f\u0670"+
		"\7\5\2\2\u0670\u0672\5\u0096L\2\u0671\u066f\3\2\2\2\u0672\u0675\3\2\2"+
		"\2\u0673\u0671\3\2\2\2\u0673\u0674\3\2\2\2\u0674\u0677\3\2\2\2\u0675\u0673"+
		"\3\2\2\2\u0676\u066e\3\2\2\2\u0676\u0677\3\2\2\2\u0677\u0678\3\2\2\2\u0678"+
		"\u0679\7\4\2\2\u0679\u0685\5\u00caf\2\u067a\u067c\7\20\2\2\u067b\u067a"+
		"\3\2\2\2\u067b\u067c\3\2\2\2\u067c\u067d\3\2\2\2\u067d\u0682\5\u00caf"+
		"\2\u067e\u067f\7\5\2\2\u067f\u0681\5\u00caf\2\u0680\u067e\3\2\2\2\u0681"+
		"\u0684\3\2\2\2\u0682\u0680\3\2\2\2\u0682\u0683\3\2\2\2\u0683\u0686\3\2"+
		"\2\2\u0684\u0682\3\2\2\2\u0685\u067b\3\2\2\2\u0685\u0686\3\2\2\2\u0686"+
		"i\3\2\2\2\u0687\u0688\t\16\2\2\u0688k\3\2\2\2\u0689\u068d\5\u0084C\2\u068a"+
		"\u068c\5n8\2\u068b\u068a\3\2\2\2\u068c\u068f\3\2\2\2\u068d\u068b\3\2\2"+
		"\2\u068d\u068e\3\2\2\2\u068em\3\2\2\2\u068f\u068d\3\2\2\2\u0690\u0691"+
		"\5p9\2\u0691\u0692\7\66\2\2\u0692\u0694\5\u0084C\2\u0693\u0695\5r:\2\u0694"+
		"\u0693\3\2\2\2\u0694\u0695\3\2\2\2\u0695\u069c\3\2\2\2\u0696\u0697\7>"+
		"\2\2\u0697\u0698\5p9\2\u0698\u0699\7\66\2\2\u0699\u069a\5\u0084C\2\u069a"+
		"\u069c\3\2\2\2\u069b\u0690\3\2\2\2\u069b\u0696\3\2\2\2\u069co\3\2\2\2"+
		"\u069d\u069f\79\2\2\u069e\u069d\3\2\2\2\u069e\u069f\3\2\2\2\u069f\u06b6"+
		"\3\2\2\2\u06a0\u06b6\7\67\2\2\u06a1\u06a3\7:\2\2\u06a2\u06a4\78\2\2\u06a3"+
		"\u06a2\3\2\2\2\u06a3\u06a4\3\2\2\2\u06a4\u06b6\3\2\2\2\u06a5\u06a6\7:"+
		"\2\2\u06a6\u06b6\7;\2\2\u06a7\u06a9\7<\2\2\u06a8\u06aa\78\2\2\u06a9\u06a8"+
		"\3\2\2\2\u06a9\u06aa\3\2\2\2\u06aa\u06b6\3\2\2\2\u06ab\u06ad\7=\2\2\u06ac"+
		"\u06ae\78\2\2\u06ad\u06ac\3\2\2\2\u06ad\u06ae\3\2\2\2\u06ae\u06b6\3\2"+
		"\2\2\u06af\u06b1\7:\2\2\u06b0\u06af\3\2\2\2\u06b0\u06b1\3\2\2\2\u06b1"+
		"\u06b2\3\2\2\2\u06b2\u06b6\7\u00f7\2\2\u06b3\u06b6\7S\2\2\u06b4\u06b6"+
		"\7U\2\2\u06b5\u069e\3\2\2\2\u06b5\u06a0\3\2\2\2\u06b5\u06a1\3\2\2\2\u06b5"+
		"\u06a5\3\2\2\2\u06b5\u06a7\3\2\2\2\u06b5\u06ab\3\2\2\2\u06b5\u06b0\3\2"+
		"\2\2\u06b5\u06b3\3\2\2\2\u06b5\u06b4\3\2\2\2\u06b6q\3\2\2\2\u06b7\u06b8"+
		"\7?\2\2\u06b8\u06c6\5\u0098M\2\u06b9\u06ba\7\u00a9\2\2\u06ba\u06bb\7\3"+
		"\2\2\u06bb\u06c0\5\u00caf\2\u06bc\u06bd\7\5\2\2\u06bd\u06bf\5\u00caf\2"+
		"\u06be\u06bc\3\2\2\2\u06bf\u06c2\3\2\2\2\u06c0\u06be\3\2\2\2\u06c0\u06c1"+
		"\3\2\2\2\u06c1\u06c3\3\2\2\2\u06c2\u06c0\3\2\2\2\u06c3\u06c4\7\4\2\2\u06c4"+
		"\u06c6\3\2\2\2\u06c5\u06b7\3\2\2\2\u06c5\u06b9\3\2\2\2\u06c6s\3\2\2\2"+
		"\u06c7\u06c8\7u\2\2\u06c8\u06ca\7\3\2\2\u06c9\u06cb\5v<\2\u06ca\u06c9"+
		"\3\2\2\2\u06ca\u06cb\3\2\2\2\u06cb\u06cc\3\2\2\2\u06cc\u06cd\7\4\2\2\u06cd"+
		"u\3\2\2\2\u06ce\u06d0\7\u0095\2\2\u06cf\u06ce\3\2\2\2\u06cf\u06d0\3\2"+
		"\2\2\u06d0\u06d1\3\2\2\2\u06d1\u06d2\t\17\2\2\u06d2\u06e7\7\u009f\2\2"+
		"\u06d3\u06d4\5\u0096L\2\u06d4\u06d5\7F\2\2\u06d5\u06e7\3\2\2\2\u06d6\u06d7"+
		"\7\u00a0\2\2\u06d7\u06d8\7\u00fe\2\2\u06d8\u06d9\7\u00a1\2\2\u06d9\u06da"+
		"\7\u00a2\2\2\u06da\u06e3\7\u00fe\2\2\u06db\u06e1\7?\2\2\u06dc\u06e2\5"+
		"\u00caf\2\u06dd\u06de\5\u00c8e\2\u06de\u06df\7\3\2\2\u06df\u06e0\7\4\2"+
		"\2\u06e0\u06e2\3\2\2\2\u06e1\u06dc\3\2\2\2\u06e1\u06dd\3\2\2\2\u06e2\u06e4"+
		"\3\2\2\2\u06e3\u06db\3\2\2\2\u06e3\u06e4\3\2\2\2\u06e4\u06e7\3\2\2\2\u06e5"+
		"\u06e7\5\u0096L\2\u06e6\u06cf\3\2\2\2\u06e6\u06d3\3\2\2\2\u06e6\u06d6"+
		"\3\2\2\2\u06e6\u06e5\3\2\2\2\u06e7w\3\2\2\2\u06e8\u06e9\7\3\2\2\u06e9"+
		"\u06ea\5z>\2\u06ea\u06eb\7\4\2\2\u06eby\3\2\2\2\u06ec\u06f1\5\u00caf\2"+
		"\u06ed\u06ee\7\5\2\2\u06ee\u06f0\5\u00caf\2\u06ef\u06ed\3\2\2\2\u06f0"+
		"\u06f3\3\2\2\2\u06f1\u06ef\3\2\2\2\u06f1\u06f2\3\2\2\2\u06f2{\3\2\2\2"+
		"\u06f3\u06f1\3\2\2\2\u06f4\u06f5\7\3\2\2\u06f5\u06fa\5~@\2\u06f6\u06f7"+
		"\7\5\2\2\u06f7\u06f9\5~@\2\u06f8\u06f6\3\2\2\2\u06f9\u06fc\3\2\2\2\u06fa"+
		"\u06f8\3\2\2\2\u06fa\u06fb\3\2\2\2\u06fb\u06fd\3\2\2\2\u06fc\u06fa\3\2"+
		"\2\2\u06fd\u06fe\7\4\2\2\u06fe}\3\2\2\2\u06ff\u0701\5\u00caf\2\u0700\u0702"+
		"\t\f\2\2\u0701\u0700\3\2\2\2\u0701\u0702\3\2\2\2\u0702\177\3\2\2\2\u0703"+
		"\u0704\7\3\2\2\u0704\u0709\5\u0082B\2\u0705\u0706\7\5\2\2\u0706\u0708"+
		"\5\u0082B\2\u0707\u0705\3\2\2\2\u0708\u070b\3\2\2\2\u0709\u0707\3\2\2"+
		"\2\u0709\u070a\3\2\2\2\u070a\u070c\3\2\2\2\u070b\u0709\3\2\2\2\u070c\u070d"+
		"\7\4\2\2\u070d\u0081\3\2\2\2\u070e\u0711\5\u00caf\2\u070f\u0710\7|\2\2"+
		"\u0710\u0712\7\u00fa\2\2\u0711\u070f\3\2\2\2\u0711\u0712\3\2\2\2\u0712"+
		"\u0083\3\2\2\2\u0713\u0715\5\u008eH\2\u0714\u0716\5t;\2\u0715\u0714\3"+
		"\2\2\2\u0715\u0716\3\2\2\2\u0716\u0717\3\2\2\2\u0717\u0718\5\u008aF\2"+
		"\u0718\u072c\3\2\2\2\u0719\u071a\7\3\2\2\u071a\u071b\5D#\2\u071b\u071d"+
		"\7\4\2\2\u071c\u071e\5t;\2\u071d\u071c\3\2\2\2\u071d\u071e\3\2\2\2\u071e"+
		"\u071f\3\2\2\2\u071f\u0720\5\u008aF\2\u0720\u072c\3\2\2\2\u0721\u0722"+
		"\7\3\2\2\u0722\u0723\5l\67\2\u0723\u0725\7\4\2\2\u0724\u0726\5t;\2\u0725"+
		"\u0724\3\2\2\2\u0725\u0726\3\2\2\2\u0726\u0727\3\2\2\2\u0727\u0728\5\u008a"+
		"F\2\u0728\u072c\3\2\2\2\u0729\u072c\5\u0086D\2\u072a\u072c\5\u0088E\2"+
		"\u072b\u0713\3\2\2\2\u072b\u0719\3\2\2\2\u072b\u0721\3\2\2\2\u072b\u0729"+
		"\3\2\2\2\u072b\u072a\3\2\2\2\u072c\u0085\3\2\2\2\u072d\u072e\7X\2\2\u072e"+
		"\u0733\5\u0096L\2\u072f\u0730\7\5\2\2\u0730\u0732\5\u0096L\2\u0731\u072f"+
		"\3\2\2\2\u0732\u0735\3\2\2\2\u0733\u0731\3\2\2\2\u0733\u0734\3\2\2\2\u0734"+
		"\u0736\3\2\2\2\u0735\u0733\3\2\2\2\u0736\u0737\5\u008aF\2\u0737\u0087"+
		"\3\2\2\2\u0738\u0739\5\u00caf\2\u0739\u0742\7\3\2\2\u073a\u073f\5\u0096"+
		"L\2\u073b\u073c\7\5\2\2\u073c\u073e\5\u0096L\2\u073d\u073b\3\2\2\2\u073e"+
		"\u0741\3\2\2\2\u073f\u073d\3\2\2\2\u073f\u0740\3\2\2\2\u0740\u0743\3\2"+
		"\2\2\u0741\u073f\3\2\2\2\u0742\u073a\3\2\2\2\u0742\u0743\3\2\2\2\u0743"+
		"\u0744\3\2\2\2\u0744\u0745\7\4\2\2\u0745\u0746\5\u008aF\2\u0746\u0089"+
		"\3\2\2\2\u0747\u0749\7\20\2\2\u0748\u0747\3\2\2\2\u0748\u0749\3\2\2\2"+
		"\u0749\u074a\3\2\2\2\u074a\u074c\5\u00ccg\2\u074b\u074d\5x=\2\u074c\u074b"+
		"\3\2\2\2\u074c\u074d\3\2\2\2\u074d\u074f\3\2\2\2\u074e\u0748\3\2\2\2\u074e"+
		"\u074f\3\2\2\2\u074f\u008b\3\2\2\2\u0750\u0751\7N\2\2\u0751\u0752\7c\2"+
		"\2\u0752\u0753\7\u00aa\2\2\u0753\u0757\7\u00fa\2\2\u0754\u0755\7O\2\2"+
		"\u0755\u0756\7\u00ab\2\2\u0756\u0758\5\60\31\2\u0757\u0754\3\2\2\2\u0757"+
		"\u0758\3\2\2\2\u0758\u0782\3\2\2\2\u0759\u075a\7N\2\2\u075a\u075b\7c\2"+
		"\2\u075b\u0765\7\u00ae\2\2\u075c\u075d\7\u00af\2\2\u075d\u075e\7\u00b0"+
		"\2\2\u075e\u075f\7\26\2\2\u075f\u0763\7\u00fa\2\2\u0760\u0761\7\u00b4"+
		"\2\2\u0761\u0762\7\26\2\2\u0762\u0764\7\u00fa\2\2\u0763\u0760\3\2\2\2"+
		"\u0763\u0764\3\2\2\2\u0764\u0766\3\2\2\2\u0765\u075c\3\2\2\2\u0765\u0766"+
		"\3\2\2\2\u0766\u076c\3\2\2\2\u0767\u0768\7\u00b1\2\2\u0768\u0769\7\u00b2"+
		"\2\2\u0769\u076a\7\u00b0\2\2\u076a\u076b\7\26\2\2\u076b\u076d\7\u00fa"+
		"\2\2\u076c\u0767\3\2\2\2\u076c\u076d\3\2\2\2\u076d\u0773\3\2\2\2\u076e"+
		"\u076f\7z\2\2\u076f\u0770\7\u00b3\2\2\u0770\u0771\7\u00b0\2\2\u0771\u0772"+
		"\7\26\2\2\u0772\u0774\7\u00fa\2\2\u0773\u076e\3\2\2\2\u0773\u0774\3\2"+
		"\2\2\u0774\u0779\3\2\2\2\u0775\u0776\7\u00b5\2\2\u0776\u0777\7\u00b0\2"+
		"\2\u0777\u0778\7\26\2\2\u0778\u077a\7\u00fa\2\2\u0779\u0775\3\2\2\2\u0779"+
		"\u077a\3\2\2\2\u077a\u077f\3\2\2\2\u077b\u077c\7)\2\2\u077c\u077d\7\u00e3"+
		"\2\2\u077d\u077e\7\20\2\2\u077e\u0780\7\u00fa\2\2\u077f\u077b\3\2\2\2"+
		"\u077f\u0780\3\2\2\2\u0780\u0782\3\2\2\2\u0781\u0750\3\2\2\2\u0781\u0759"+
		"\3\2\2\2\u0782\u008d\3\2\2\2\u0783\u0784\5\u00caf\2\u0784\u0785\7\6\2"+
		"\2\u0785\u0787\3\2\2\2\u0786\u0783\3\2\2\2\u0786\u0787\3\2\2\2\u0787\u0788"+
		"\3\2\2\2\u0788\u0789\5\u00caf\2\u0789\u008f\3\2\2\2\u078a\u078b\5\u00ca"+
		"f\2\u078b\u078c\7\6\2\2\u078c\u078e\3\2\2\2\u078d\u078a\3\2\2\2\u078d"+
		"\u078e\3\2\2\2\u078e\u078f\3\2\2\2\u078f\u0790\5\u00caf\2\u0790\u0091"+
		"\3\2\2\2\u0791\u0799\5\u0096L\2\u0792\u0794\7\20\2\2\u0793\u0792\3\2\2"+
		"\2\u0793\u0794\3\2\2\2\u0794\u0797\3\2\2\2\u0795\u0798\5\u00caf\2\u0796"+
		"\u0798\5x=\2\u0797\u0795\3\2\2\2\u0797\u0796\3\2\2\2\u0798\u079a\3\2\2"+
		"\2\u0799\u0793\3\2\2\2\u0799\u079a\3\2\2\2\u079a\u0093\3\2\2\2\u079b\u07a0"+
		"\5\u0092J\2\u079c\u079d\7\5\2\2\u079d\u079f\5\u0092J\2\u079e\u079c\3\2"+
		"\2\2\u079f\u07a2\3\2\2\2\u07a0\u079e\3\2\2\2\u07a0\u07a1\3\2\2\2\u07a1"+
		"\u0095\3\2\2\2\u07a2\u07a0\3\2\2\2\u07a3\u07a4\5\u0098M\2\u07a4\u0097"+
		"\3\2\2\2\u07a5\u07a6\bM\1\2\u07a6\u07a7\7\"\2\2\u07a7\u07b2\5\u0098M\7"+
		"\u07a8\u07a9\7$\2\2\u07a9\u07aa\7\3\2\2\u07aa\u07ab\5\34\17\2\u07ab\u07ac"+
		"\7\4\2\2\u07ac\u07b2\3\2\2\2\u07ad\u07af\5\u009cO\2\u07ae\u07b0\5\u009a"+
		"N\2\u07af\u07ae\3\2\2\2\u07af\u07b0\3\2\2\2\u07b0\u07b2\3\2\2\2\u07b1"+
		"\u07a5\3\2\2\2\u07b1\u07a8\3\2\2\2\u07b1\u07ad\3\2\2\2\u07b2\u07bb\3\2"+
		"\2\2\u07b3\u07b4\f\4\2\2\u07b4\u07b5\7 \2\2\u07b5\u07ba\5\u0098M\5\u07b6"+
		"\u07b7\f\3\2\2\u07b7\u07b8\7\37\2\2\u07b8\u07ba\5\u0098M\4\u07b9\u07b3"+
		"\3\2\2\2\u07b9\u07b6\3\2\2\2\u07ba\u07bd\3\2\2\2\u07bb\u07b9\3\2\2\2\u07bb"+
		"\u07bc\3\2\2\2\u07bc\u0099\3\2\2\2\u07bd\u07bb\3\2\2\2\u07be\u07c0\7\""+
		"\2\2\u07bf\u07be\3\2\2\2\u07bf\u07c0\3\2\2\2\u07c0\u07c1\3\2\2\2\u07c1"+
		"\u07c2\7%\2\2\u07c2\u07c3\5\u009cO\2\u07c3\u07c4\7 \2\2\u07c4\u07c5\5"+
		"\u009cO\2\u07c5\u07fb\3\2\2\2\u07c6\u07c8\7\"\2\2\u07c7\u07c6\3\2\2\2"+
		"\u07c7\u07c8\3\2\2\2\u07c8\u07c9\3\2\2\2\u07c9\u07ca\7!\2\2\u07ca\u07cb"+
		"\7\3\2\2\u07cb\u07d0\5\u0096L\2\u07cc\u07cd\7\5\2\2\u07cd\u07cf\5\u0096"+
		"L\2\u07ce\u07cc\3\2\2\2\u07cf\u07d2\3\2\2\2\u07d0\u07ce\3\2\2\2\u07d0"+
		"\u07d1\3\2\2\2\u07d1\u07d3\3\2\2\2\u07d2\u07d0\3\2\2\2\u07d3\u07d4\7\4"+
		"\2\2\u07d4\u07fb\3\2\2\2\u07d5\u07d7\7\"\2\2\u07d6\u07d5\3\2\2\2\u07d6"+
		"\u07d7\3\2\2\2\u07d7\u07d8\3\2\2\2\u07d8\u07d9\7!\2\2\u07d9\u07da\5\u00c8"+
		"e\2\u07da\u07db\7\3\2\2\u07db\u07dc\5\u0096L\2\u07dc\u07dd\7\5\2\2\u07dd"+
		"\u07de\5\u0096L\2\u07de\u07df\3\2\2\2\u07df\u07e0\7\4\2\2\u07e0\u07fb"+
		"\3\2\2\2\u07e1\u07e3\7\"\2\2\u07e2\u07e1\3\2\2\2\u07e2\u07e3\3\2\2\2\u07e3"+
		"\u07e4\3\2\2\2\u07e4\u07e5\7!\2\2\u07e5\u07e6\7\3\2\2\u07e6\u07e7\5\34"+
		"\17\2\u07e7\u07e8\7\4\2\2\u07e8\u07fb\3\2\2\2\u07e9\u07eb\7\"\2\2\u07ea"+
		"\u07e9\3\2\2\2\u07ea\u07eb\3\2\2\2\u07eb\u07ec\3\2\2\2\u07ec\u07ed\t\20"+
		"\2\2\u07ed\u07fb\5\u009cO\2\u07ee\u07f0\7(\2\2\u07ef\u07f1\7\"\2\2\u07f0"+
		"\u07ef\3\2\2\2\u07f0\u07f1\3\2\2\2\u07f1\u07f2\3\2\2\2\u07f2\u07fb\7)"+
		"\2\2\u07f3\u07f5\7(\2\2\u07f4\u07f6\7\"\2\2\u07f5\u07f4\3\2\2\2\u07f5"+
		"\u07f6\3\2\2\2\u07f6\u07f7\3\2\2\2\u07f7\u07f8\7\23\2\2\u07f8\u07f9\7"+
		"\16\2\2\u07f9\u07fb\5\u009cO\2\u07fa\u07bf\3\2\2\2\u07fa\u07c7\3\2\2\2"+
		"\u07fa\u07d6\3\2\2\2\u07fa\u07e2\3\2\2\2\u07fa\u07ea\3\2\2\2\u07fa\u07ee"+
		"\3\2\2\2\u07fa\u07f3\3\2\2\2\u07fb\u009b\3\2\2\2\u07fc\u07fd\bO\1\2\u07fd"+
		"\u0801\5\u009eP\2\u07fe\u07ff\t\21\2\2\u07ff\u0801\5\u009cO\t\u0800\u07fc"+
		"\3\2\2\2\u0800\u07fe\3\2\2\2\u0801\u0817\3\2\2\2\u0802\u0803\f\b\2\2\u0803"+
		"\u0804\t\22\2\2\u0804\u0816\5\u009cO\t\u0805\u0806\f\7\2\2\u0806\u0807"+
		"\t\23\2\2\u0807\u0816\5\u009cO\b\u0808\u0809\f\6\2\2\u0809\u080a\7\u009b"+
		"\2\2\u080a\u0816\5\u009cO\7\u080b\u080c\f\5\2\2\u080c\u080d\7\u009e\2"+
		"\2\u080d\u0816\5\u009cO\6\u080e\u080f\f\4\2\2\u080f\u0810\7\u009c\2\2"+
		"\u0810\u0816\5\u009cO\5\u0811\u0812\f\3\2\2\u0812\u0813\5\u00a2R\2\u0813"+
		"\u0814\5\u009cO\4\u0814\u0816\3\2\2\2\u0815\u0802\3\2\2\2\u0815\u0805"+
		"\3\2\2\2\u0815\u0808\3\2\2\2\u0815\u080b\3\2\2\2\u0815\u080e\3\2\2\2\u0815"+
		"\u0811\3\2\2\2\u0816\u0819\3\2\2\2\u0817\u0815\3\2\2\2\u0817\u0818\3\2"+
		"\2\2\u0818\u009d\3\2\2\2\u0819\u0817\3\2\2\2\u081a\u081b\bP\1\2\u081b"+
		"\u081d\7\61\2\2\u081c\u081e\5\u00bc_\2\u081d\u081c\3\2\2\2\u081e\u081f"+
		"\3\2\2\2\u081f\u081d\3\2\2\2\u081f\u0820\3\2\2\2\u0820\u0823\3\2\2\2\u0821"+
		"\u0822\7\64\2\2\u0822\u0824\5\u0096L\2\u0823\u0821\3\2\2\2\u0823\u0824"+
		"\3\2\2\2\u0824\u0825\3\2\2\2\u0825\u0826\7\65\2\2\u0826\u08ac\3\2\2\2"+
		"\u0827\u0828\7\61\2\2\u0828\u082a\5\u0096L\2\u0829\u082b\5\u00bc_\2\u082a"+
		"\u0829\3\2\2\2\u082b\u082c\3\2\2\2\u082c\u082a\3\2\2\2\u082c\u082d\3\2"+
		"\2\2\u082d\u0830\3\2\2\2\u082e\u082f\7\64\2\2\u082f\u0831\5\u0096L\2\u0830"+
		"\u082e\3\2\2\2\u0830\u0831\3\2\2\2\u0831\u0832\3\2\2\2\u0832\u0833\7\65"+
		"\2\2\u0833\u08ac\3\2\2\2\u0834\u0835\7g\2\2\u0835\u0836\7\3\2\2\u0836"+
		"\u0837\5\u0096L\2\u0837\u0838\7\20\2\2\u0838\u0839\5\u00b2Z\2\u0839\u083a"+
		"\7\4\2\2\u083a\u08ac\3\2\2\2\u083b\u083c\7{\2\2\u083c\u0845\7\3\2\2\u083d"+
		"\u0842\5\u0092J\2\u083e\u083f\7\5\2\2\u083f\u0841\5\u0092J\2\u0840\u083e"+
		"\3\2\2\2\u0841\u0844\3\2\2\2\u0842\u0840\3\2\2\2\u0842\u0843\3\2\2\2\u0843"+
		"\u0846\3\2\2\2\u0844\u0842\3\2\2\2\u0845\u083d\3\2\2\2\u0845\u0846\3\2"+
		"\2\2\u0846\u0847\3\2\2\2\u0847\u08ac\7\4\2\2\u0848\u0849\7K\2\2\u0849"+
		"\u084a\7\3\2\2\u084a\u084d\5\u0096L\2\u084b\u084c\7\u0085\2\2\u084c\u084e"+
		"\7,\2\2\u084d\u084b\3\2\2\2\u084d\u084e\3\2\2\2\u084e\u084f\3\2\2\2\u084f"+
		"\u0850\7\4\2\2\u0850\u08ac\3\2\2\2\u0851\u0852\7M\2\2\u0852\u0853\7\3"+
		"\2\2\u0853\u0856\5\u0096L\2\u0854\u0855\7\u0085\2\2\u0855\u0857\7,\2\2"+
		"\u0856\u0854\3\2\2\2\u0856\u0857\3\2\2\2\u0857\u0858\3\2\2\2\u0858\u0859"+
		"\7\4\2\2\u0859\u08ac\3\2\2\2\u085a\u085b\7\u008a\2\2\u085b\u085c\7\3\2"+
		"\2\u085c\u085d\5\u009cO\2\u085d\u085e\7!\2\2\u085e\u085f\5\u009cO\2\u085f"+
		"\u0860\7\4\2\2\u0860\u08ac\3\2\2\2\u0861\u08ac\5\u00a0Q\2\u0862\u08ac"+
		"\7\u0096\2\2\u0863\u0864\5\u00c8e\2\u0864\u0865\7\6\2\2\u0865\u0866\7"+
		"\u0096\2\2\u0866\u08ac\3\2\2\2\u0867\u0868\7\3\2\2\u0868\u086b\5\u0092"+
		"J\2\u0869\u086a\7\5\2\2\u086a\u086c\5\u0092J\2\u086b\u0869\3\2\2\2\u086c"+
		"\u086d\3\2\2\2\u086d\u086b\3\2\2\2\u086d\u086e\3\2\2\2\u086e\u086f\3\2"+
		"\2\2\u086f\u0870\7\4\2\2\u0870\u08ac\3\2\2\2\u0871\u0872\7\3\2\2\u0872"+
		"\u0873\5\34\17\2\u0873\u0874\7\4\2\2\u0874\u08ac\3\2\2\2\u0875\u0876\5"+
		"\u00c8e\2\u0876\u0882\7\3\2\2\u0877\u0879\5j\66\2\u0878\u0877\3\2\2\2"+
		"\u0878\u0879\3\2\2\2\u0879\u087a\3\2\2\2\u087a\u087f\5\u0096L\2\u087b"+
		"\u087c\7\5\2\2\u087c\u087e\5\u0096L\2\u087d\u087b\3\2\2\2\u087e\u0881"+
		"\3\2\2\2\u087f\u087d\3\2\2\2\u087f\u0880\3\2\2\2\u0880\u0883\3\2\2\2\u0881"+
		"\u087f\3\2\2\2\u0882\u0878\3\2\2\2\u0882\u0883\3\2\2\2\u0883\u0884\3\2"+
		"\2\2\u0884\u0887\7\4\2\2\u0885\u0886\7C\2\2\u0886\u0888\5\u00c2b\2\u0887"+
		"\u0885\3\2\2\2\u0887\u0888\3\2\2\2\u0888\u08ac\3\2\2\2\u0889\u088a\5\u00c8"+
		"e\2\u088a\u088b\7\3\2\2\u088b\u088c\t\24\2\2\u088c\u088d\5\u0096L\2\u088d"+
		"\u088e\7\16\2\2\u088e\u088f\5\u0096L\2\u088f\u0890\7\4\2\2\u0890\u08ac"+
		"\3\2\2\2\u0891\u0892\7\u0103\2\2\u0892\u0893\7\t\2\2\u0893\u08ac\5\u0096"+
		"L\2\u0894\u0895\7\3\2\2\u0895\u0898\7\u0103\2\2\u0896\u0897\7\5\2\2\u0897"+
		"\u0899\7\u0103\2\2\u0898\u0896\3\2\2\2\u0899\u089a\3\2\2\2\u089a\u0898"+
		"\3\2\2\2\u089a\u089b\3\2\2\2\u089b\u089c\3\2\2\2\u089c\u089d\7\4\2\2\u089d"+
		"\u089e\7\t\2\2\u089e\u08ac\5\u0096L\2\u089f\u08ac\5\u00caf\2\u08a0\u08a1"+
		"\7\3\2\2\u08a1\u08a2\5\u0096L\2\u08a2\u08a3\7\4\2\2\u08a3\u08ac\3\2\2"+
		"\2\u08a4\u08a5\7\u008b\2\2\u08a5\u08a6\7\3\2\2\u08a6\u08a7\5\u00caf\2"+
		"\u08a7\u08a8\7\16\2\2\u08a8\u08a9\5\u009cO\2\u08a9\u08aa\7\4\2\2\u08aa"+
		"\u08ac\3\2\2\2\u08ab\u081a\3\2\2\2\u08ab\u0827\3\2\2\2\u08ab\u0834\3\2"+
		"\2\2\u08ab\u083b\3\2\2\2\u08ab\u0848\3\2\2\2\u08ab\u0851\3\2\2\2\u08ab"+
		"\u085a\3\2\2\2\u08ab\u0861\3\2\2\2\u08ab\u0862\3\2\2\2\u08ab\u0863\3\2"+
		"\2\2\u08ab\u0867\3\2\2\2\u08ab\u0871\3\2\2\2\u08ab\u0875\3\2\2\2\u08ab"+
		"\u0889\3\2\2\2\u08ab\u0891\3\2\2\2\u08ab\u0894\3\2\2\2\u08ab\u089f\3\2"+
		"\2\2\u08ab\u08a0\3\2\2\2\u08ab\u08a4\3\2\2\2\u08ac\u08b7\3\2\2\2\u08ad"+
		"\u08ae\f\7\2\2\u08ae\u08af\7\n\2\2\u08af\u08b0\5\u009cO\2\u08b0\u08b1"+
		"\7\13\2\2\u08b1\u08b6\3\2\2\2\u08b2\u08b3\f\5\2\2\u08b3\u08b4\7\6\2\2"+
		"\u08b4\u08b6\5\u00caf\2\u08b5\u08ad\3\2\2\2\u08b5\u08b2\3\2\2\2\u08b6"+
		"\u08b9\3\2\2\2\u08b7\u08b5\3\2\2\2\u08b7\u08b8\3\2\2\2\u08b8\u009f\3\2"+
		"\2\2\u08b9\u08b7\3\2\2\2\u08ba\u08c7\7)\2\2\u08bb\u08c7\5\u00aaV\2\u08bc"+
		"\u08bd\5\u00caf\2\u08bd\u08be\7\u00fa\2\2\u08be\u08c7\3\2\2\2\u08bf\u08c7"+
		"\5\u00d0i\2\u08c0\u08c7\5\u00a8U\2\u08c1\u08c3\7\u00fa\2\2\u08c2\u08c1"+
		"\3\2\2\2\u08c3\u08c4\3\2\2\2\u08c4\u08c2\3\2\2\2\u08c4\u08c5\3\2\2\2\u08c5"+
		"\u08c7\3\2\2\2\u08c6\u08ba\3\2\2\2\u08c6\u08bb\3\2\2\2\u08c6\u08bc\3\2"+
		"\2\2\u08c6\u08bf\3\2\2\2\u08c6\u08c0\3\2\2\2\u08c6\u08c2\3\2\2\2\u08c7"+
		"\u00a1\3\2\2\2\u08c8\u08c9\t\25\2\2\u08c9\u00a3\3\2\2\2\u08ca\u08cb\t"+
		"\26\2\2\u08cb\u00a5\3\2\2\2\u08cc\u08cd\t\27\2\2\u08cd\u00a7\3\2\2\2\u08ce"+
		"\u08cf\t\30\2\2\u08cf\u00a9\3\2\2\2\u08d0\u08d4\7\60\2\2\u08d1\u08d3\5"+
		"\u00acW\2\u08d2\u08d1\3\2\2\2\u08d3\u08d6\3\2\2\2\u08d4\u08d2\3\2\2\2"+
		"\u08d4\u08d5\3\2\2\2\u08d5\u00ab\3\2\2\2\u08d6\u08d4\3\2\2\2\u08d7\u08d8"+
		"\5\u00aeX\2\u08d8\u08db\5\u00caf\2\u08d9\u08da\7t\2\2\u08da\u08dc\5\u00ca"+
		"f\2\u08db\u08d9\3\2\2\2\u08db\u08dc\3\2\2\2\u08dc\u00ad\3\2\2\2\u08dd"+
		"\u08df\t\31\2\2\u08de\u08dd\3\2\2\2\u08de\u08df\3\2\2\2\u08df\u08e0\3"+
		"\2\2\2\u08e0\u08e3\t\17\2\2\u08e1\u08e3\7\u00fa\2\2\u08e2\u08de\3\2\2"+
		"\2\u08e2\u08e1\3\2\2\2\u08e3\u00af\3\2\2\2\u08e4\u08e8\7K\2\2\u08e5\u08e6"+
		"\7L\2\2\u08e6\u08e8\5\u00caf\2\u08e7\u08e4\3\2\2\2\u08e7\u08e5\3\2\2\2"+
		"\u08e8\u00b1\3\2\2\2\u08e9\u08ea\7y\2\2\u08ea\u08eb\7\u0090\2\2\u08eb"+
		"\u08ec\5\u00b2Z\2\u08ec\u08ed\7\u0092\2\2\u08ed\u090c\3\2\2\2\u08ee\u08ef"+
		"\7z\2\2\u08ef\u08f0\7\u0090\2\2\u08f0\u08f1\5\u00b2Z\2\u08f1\u08f2\7\5"+
		"\2\2\u08f2\u08f3\5\u00b2Z\2\u08f3\u08f4\7\u0092\2\2\u08f4\u090c\3\2\2"+
		"\2\u08f5\u08fc\7{\2\2\u08f6\u08f8\7\u0090\2\2\u08f7\u08f9\5\u00b8]\2\u08f8"+
		"\u08f7\3\2\2\2\u08f8\u08f9\3\2\2\2\u08f9\u08fa\3\2\2\2\u08fa\u08fd\7\u0092"+
		"\2\2\u08fb\u08fd\7\u008e\2\2\u08fc\u08f6\3\2\2\2\u08fc\u08fb\3\2\2\2\u08fd"+
		"\u090c\3\2\2\2\u08fe\u0909\5\u00caf\2\u08ff\u0900\7\3\2\2\u0900\u0905"+
		"\7\u00fe\2\2\u0901\u0902\7\5\2\2\u0902\u0904\7\u00fe\2\2\u0903\u0901\3"+
		"\2\2\2\u0904\u0907\3\2\2\2\u0905\u0903\3\2\2\2\u0905\u0906\3\2\2\2\u0906"+
		"\u0908\3\2\2\2\u0907\u0905\3\2\2\2\u0908\u090a\7\4\2\2\u0909\u08ff\3\2"+
		"\2\2\u0909\u090a\3\2\2\2\u090a\u090c\3\2\2\2\u090b\u08e9\3\2\2\2\u090b"+
		"\u08ee\3\2\2\2\u090b\u08f5\3\2\2\2\u090b\u08fe\3\2\2\2\u090c\u00b3\3\2"+
		"\2\2\u090d\u0912\5\u00b6\\\2\u090e\u090f\7\5\2\2\u090f\u0911\5\u00b6\\"+
		"\2\u0910\u090e\3\2\2\2\u0911\u0914\3\2\2\2\u0912\u0910\3\2\2\2\u0912\u0913"+
		"\3\2\2\2\u0913\u00b5\3\2\2\2\u0914\u0912\3\2\2\2\u0915\u0916\5\u00caf"+
		"\2\u0916\u0919\5\u00b2Z\2\u0917\u0918\7|\2\2\u0918\u091a\7\u00fa\2\2\u0919"+
		"\u0917\3\2\2\2\u0919\u091a\3\2\2\2\u091a\u00b7\3\2\2";
	private static final String _serializedATNSegment1 =
		"\2\u091b\u0920\5\u00ba^\2\u091c\u091d\7\5\2\2\u091d\u091f\5\u00ba^\2\u091e"+
		"\u091c\3\2\2\2\u091f\u0922\3\2\2\2\u0920\u091e\3\2\2\2\u0920\u0921\3\2"+
		"\2\2\u0921\u00b9\3\2\2\2\u0922\u0920\3\2\2\2\u0923\u0924\5\u00caf\2\u0924"+
		"\u0925\7\f\2\2\u0925\u0928\5\u00b2Z\2\u0926\u0927\7|\2\2\u0927\u0929\7"+
		"\u00fa\2\2\u0928\u0926\3\2\2\2\u0928\u0929\3\2\2\2\u0929\u00bb\3\2\2\2"+
		"\u092a\u092b\7\62\2\2\u092b\u092c\5\u0096L\2\u092c\u092d\7\63\2\2\u092d"+
		"\u092e\5\u0096L\2\u092e\u00bd\3\2\2\2\u092f\u0930\7B\2\2\u0930\u0935\5"+
		"\u00c0a\2\u0931\u0932\7\5\2\2\u0932\u0934\5\u00c0a\2\u0933\u0931\3\2\2"+
		"\2\u0934\u0937\3\2\2\2\u0935\u0933\3\2\2\2\u0935\u0936\3\2\2\2\u0936\u00bf"+
		"\3\2\2\2\u0937\u0935\3\2\2\2\u0938\u0939\5\u00caf\2\u0939\u093a\7\20\2"+
		"\2\u093a\u093b\5\u00c2b\2\u093b\u00c1\3\2\2\2\u093c\u096b\5\u00caf\2\u093d"+
		"\u093e\7\3\2\2\u093e\u093f\5\u00caf\2\u093f\u0940\7\4\2\2\u0940\u096b"+
		"\3\2\2\2\u0941\u0964\7\3\2\2\u0942\u0943\7\u00a4\2\2\u0943\u0944\7\26"+
		"\2\2\u0944\u0949\5\u0096L\2\u0945\u0946\7\5\2\2\u0946\u0948\5\u0096L\2"+
		"\u0947\u0945\3\2\2\2\u0948\u094b\3\2\2\2\u0949\u0947\3\2\2\2\u0949\u094a"+
		"\3\2\2\2\u094a\u0965\3\2\2\2\u094b\u0949\3\2\2\2\u094c\u094d\t\32\2\2"+
		"\u094d\u094e\7\26\2\2\u094e\u0953\5\u0096L\2\u094f\u0950\7\5\2\2\u0950"+
		"\u0952\5\u0096L\2\u0951\u094f\3\2\2\2\u0952\u0955\3\2\2\2\u0953\u0951"+
		"\3\2\2\2\u0953\u0954\3\2\2\2\u0954\u0957\3\2\2\2\u0955\u0953\3\2\2\2\u0956"+
		"\u094c\3\2\2\2\u0956\u0957\3\2\2\2\u0957\u0962\3\2\2\2\u0958\u0959\t\33"+
		"\2\2\u0959\u095a\7\26\2\2\u095a\u095f\5N(\2\u095b\u095c\7\5\2\2\u095c"+
		"\u095e\5N(\2\u095d\u095b\3\2\2\2\u095e\u0961\3\2\2\2\u095f\u095d\3\2\2"+
		"\2\u095f\u0960\3\2\2\2\u0960\u0963\3\2\2\2\u0961\u095f\3\2\2\2\u0962\u0958"+
		"\3\2\2\2\u0962\u0963\3\2\2\2\u0963\u0965\3\2\2\2\u0964\u0942\3\2\2\2\u0964"+
		"\u0956\3\2\2\2\u0965\u0967\3\2\2\2\u0966\u0968\5\u00c4c\2\u0967\u0966"+
		"\3\2\2\2\u0967\u0968\3\2\2\2\u0968\u0969\3\2\2\2\u0969\u096b\7\4\2\2\u096a"+
		"\u093c\3\2\2\2\u096a\u093d\3\2\2\2\u096a\u0941\3\2\2\2\u096b\u00c3\3\2"+
		"\2\2\u096c\u096d\7E\2\2\u096d\u097d\5\u00c6d\2\u096e\u096f\7F\2\2\u096f"+
		"\u097d\5\u00c6d\2\u0970\u0971\7E\2\2\u0971\u0972\7%\2\2\u0972\u0973\5"+
		"\u00c6d\2\u0973\u0974\7 \2\2\u0974\u0975\5\u00c6d\2\u0975\u097d\3\2\2"+
		"\2\u0976\u0977\7F\2\2\u0977\u0978\7%\2\2\u0978\u0979\5\u00c6d\2\u0979"+
		"\u097a\7 \2\2\u097a\u097b\5\u00c6d\2\u097b\u097d\3\2\2\2\u097c\u096c\3"+
		"\2\2\2\u097c\u096e\3\2\2\2\u097c\u0970\3\2\2\2\u097c\u0976\3\2\2\2\u097d"+
		"\u00c5\3\2\2\2\u097e\u097f\7G\2\2\u097f\u0986\t\34\2\2\u0980\u0981\7J"+
		"\2\2\u0981\u0986\7N\2\2\u0982\u0983\5\u0096L\2\u0983\u0984\t\34\2\2\u0984"+
		"\u0986\3\2\2\2\u0985\u097e\3\2\2\2\u0985\u0980\3\2\2\2\u0985\u0982\3\2"+
		"\2\2\u0986\u00c7\3\2\2\2\u0987\u098c\5\u00caf\2\u0988\u0989\7\6\2\2\u0989"+
		"\u098b\5\u00caf\2\u098a\u0988\3\2\2\2\u098b\u098e\3\2\2\2\u098c\u098a"+
		"\3\2\2\2\u098c\u098d\3\2\2\2\u098d\u00c9\3\2\2\2\u098e\u098c\3\2\2\2\u098f"+
		"\u099f\5\u00ccg\2\u0990\u099f\7\u00f7\2\2\u0991\u099f\7=\2\2\u0992\u099f"+
		"\79\2\2\u0993\u099f\7:\2\2\u0994\u099f\7;\2\2\u0995\u099f\7<\2\2\u0996"+
		"\u099f\7>\2\2\u0997\u099f\7\66\2\2\u0998\u099f\7\67\2\2\u0999\u099f\7"+
		"?\2\2\u099a\u099f\7p\2\2\u099b\u099f\7s\2\2\u099c\u099f\7q\2\2\u099d\u099f"+
		"\7r\2\2\u099e\u098f\3\2\2\2\u099e\u0990\3\2\2\2\u099e\u0991\3\2\2\2\u099e"+
		"\u0992\3\2\2\2\u099e\u0993\3\2\2\2\u099e\u0994\3\2\2\2\u099e\u0995\3\2"+
		"\2\2\u099e\u0996\3\2\2\2\u099e\u0997\3\2\2\2\u099e\u0998\3\2\2\2\u099e"+
		"\u0999\3\2\2\2\u099e\u099a\3\2\2\2\u099e\u099b\3\2\2\2\u099e\u099c\3\2"+
		"\2\2\u099e\u099d\3\2\2\2\u099f\u00cb\3\2\2\2\u09a0\u09a4\7\u0103\2\2\u09a1"+
		"\u09a4\5\u00ceh\2\u09a2\u09a4\5\u00d2j\2\u09a3\u09a0\3\2\2\2\u09a3\u09a1"+
		"\3\2\2\2\u09a3\u09a2\3\2\2\2\u09a4\u00cd\3\2\2\2\u09a5\u09a6\7\u0104\2"+
		"\2\u09a6\u00cf\3\2\2\2\u09a7\u09a9\7\u0095\2\2\u09a8\u09a7\3\2\2\2\u09a8"+
		"\u09a9\3\2\2\2\u09a9\u09aa\3\2\2\2\u09aa\u09c4\7\u00ff\2\2\u09ab\u09ad"+
		"\7\u0095\2\2\u09ac\u09ab\3\2\2\2\u09ac\u09ad\3\2\2\2\u09ad\u09ae\3\2\2"+
		"\2\u09ae\u09c4\7\u00fe\2\2\u09af\u09b1\7\u0095\2\2\u09b0\u09af\3\2\2\2"+
		"\u09b0\u09b1\3\2\2\2\u09b1\u09b2\3\2\2\2\u09b2\u09c4\7\u00fb\2\2\u09b3"+
		"\u09b5\7\u0095\2\2\u09b4\u09b3\3\2\2\2\u09b4\u09b5\3\2\2\2\u09b5\u09b6"+
		"\3\2\2\2\u09b6\u09c4\7\u00fc\2\2\u09b7\u09b9\7\u0095\2\2\u09b8\u09b7\3"+
		"\2\2\2\u09b8\u09b9\3\2\2\2\u09b9\u09ba\3\2\2\2\u09ba\u09c4\7\u00fd\2\2"+
		"\u09bb\u09bd\7\u0095\2\2\u09bc\u09bb\3\2\2\2\u09bc\u09bd\3\2\2\2\u09bd"+
		"\u09be\3\2\2\2\u09be\u09c4\7\u0100\2\2\u09bf\u09c1\7\u0095\2\2\u09c0\u09bf"+
		"\3\2\2\2\u09c0\u09c1\3\2\2\2\u09c1\u09c2\3\2\2\2\u09c2\u09c4\7\u0101\2"+
		"\2\u09c3\u09a8\3\2\2\2\u09c3\u09ac\3\2\2\2\u09c3\u09b0\3\2\2\2\u09c3\u09b4"+
		"\3\2\2\2\u09c3\u09b8\3\2\2\2\u09c3\u09bc\3\2\2\2\u09c3\u09c0\3\2\2\2\u09c4"+
		"\u00d1\3\2\2\2\u09c5\u09c6\t\35\2\2\u09c6\u00d3\3\2\2\2\u014a\u00ee\u00f3"+
		"\u00f6\u00fb\u0108\u010c\u0113\u0121\u0123\u0127\u012a\u0131\u0142\u0144"+
		"\u0148\u014b\u0152\u0158\u015e\u0166\u0186\u018e\u0192\u0197\u019d\u01a5"+
		"\u01ab\u01b8\u01bd\u01c6\u01cb\u01db\u01e2\u01e6\u01ee\u01f5\u01fc\u020b"+
		"\u020f\u0215\u021b\u021e\u0221\u0227\u022b\u022f\u0234\u0238\u0240\u0243"+
		"\u024c\u0251\u0257\u025e\u0261\u0267\u0272\u0275\u0279\u027e\u0283\u028a"+
		"\u028d\u0290\u0297\u029c\u02a1\u02a4\u02ad\u02b5\u02bb\u02bf\u02c3\u02c7"+
		"\u02c9\u02d2\u02d8\u02dd\u02e0\u02e4\u02e7\u02f1\u02f4\u02f8\u02fd\u0300"+
		"\u0306\u030e\u0313\u0319\u031f\u032a\u0332\u0339\u0353\u0356\u0360\u0364"+
		"\u036b\u03d9\u03e1\u03e9\u03f2\u03fe\u0402\u0405\u040b\u0415\u0421\u0426"+
		"\u042c\u0438\u043a\u043f\u0443\u0448\u044d\u0450\u0455\u0459\u045e\u0460"+
		"\u0464\u046d\u0475\u047c\u0483\u048c\u0491\u04a0\u04a7\u04aa\u04b1\u04b5"+
		"\u04bb\u04c3\u04ce\u04d9\u04e0\u04e6\u04ec\u04f5\u04f7\u0500\u0503\u050c"+
		"\u050f\u0518\u051b\u0524\u0527\u052a\u052f\u0531\u0534\u0540\u0547\u054e"+
		"\u0551\u0553\u055e\u0562\u0566\u0572\u0575\u0579\u0583\u0587\u0589\u058c"+
		"\u0590\u0593\u0597\u059d\u05a1\u05a5\u05aa\u05ad\u05af\u05b4\u05b9\u05bc"+
		"\u05c0\u05c3\u05c6\u05c8\u05d8\u05dd\u05ea\u05ef\u05f7\u05fd\u0601\u060a"+
		"\u0619\u061e\u062a\u062f\u0637\u063a\u063e\u064c\u0659\u065e\u0662\u0665"+
		"\u066a\u0673\u0676\u067b\u0682\u0685\u068d\u0694\u069b\u069e\u06a3\u06a9"+
		"\u06ad\u06b0\u06b5\u06c0\u06c5\u06ca\u06cf\u06e1\u06e3\u06e6\u06f1\u06fa"+
		"\u0701\u0709\u0711\u0715\u071d\u0725\u072b\u0733\u073f\u0742\u0748\u074c"+
		"\u074e\u0757\u0763\u0765\u076c\u0773\u0779\u077f\u0781\u0786\u078d\u0793"+
		"\u0797\u0799\u07a0\u07af\u07b1\u07b9\u07bb\u07bf\u07c7\u07d0\u07d6\u07e2"+
		"\u07ea\u07f0\u07f5\u07fa\u0800\u0815\u0817\u081f\u0823\u082c\u0830\u0842"+
		"\u0845\u084d\u0856\u086d\u0878\u087f\u0882\u0887\u089a\u08ab\u08b5\u08b7"+
		"\u08c4\u08c6\u08d4\u08db\u08de\u08e2\u08e7\u08f8\u08fc\u0905\u0909\u090b"+
		"\u0912\u0919\u0920\u0928\u0935\u0949\u0953\u0956\u095f\u0962\u0964\u0967"+
		"\u096a\u097c\u0985\u098c\u099e\u09a3\u09a8\u09ac\u09b0\u09b4\u09b8\u09bc"+
		"\u09c0\u09c3";
	public static final String _serializedATN = Utils.join(
		new String[] {
			_serializedATNSegment0,
			_serializedATNSegment1
		},
		""
	);
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}