// Generated from C:/Users/admin/IdeaProjects/Siamese/src/main/antlr4/org/daslab\NewSqlBase.g4 by ANTLR 4.8
package org.daslab;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NewSqlBaseParser}.
 */
public interface NewSqlBaseListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSingleStatement(NewSqlBaseParser.SingleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSingleStatement(NewSqlBaseParser.SingleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterSingleExpression(NewSqlBaseParser.SingleExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitSingleExpression(NewSqlBaseParser.SingleExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#singleTableIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSingleTableIdentifier(NewSqlBaseParser.SingleTableIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#singleTableIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSingleTableIdentifier(NewSqlBaseParser.SingleTableIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#singleFunctionIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSingleFunctionIdentifier(NewSqlBaseParser.SingleFunctionIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#singleFunctionIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSingleFunctionIdentifier(NewSqlBaseParser.SingleFunctionIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#singleDataType}.
	 * @param ctx the parse tree
	 */
	void enterSingleDataType(NewSqlBaseParser.SingleDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#singleDataType}.
	 * @param ctx the parse tree
	 */
	void exitSingleDataType(NewSqlBaseParser.SingleDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#singleTableSchema}.
	 * @param ctx the parse tree
	 */
	void enterSingleTableSchema(NewSqlBaseParser.SingleTableSchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#singleTableSchema}.
	 * @param ctx the parse tree
	 */
	void exitSingleTableSchema(NewSqlBaseParser.SingleTableSchemaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementDefault(NewSqlBaseParser.StatementDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementDefault(NewSqlBaseParser.StatementDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code use}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUse(NewSqlBaseParser.UseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code use}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUse(NewSqlBaseParser.UseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createDatabase}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateDatabase(NewSqlBaseParser.CreateDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createDatabase}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateDatabase(NewSqlBaseParser.CreateDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setDatabaseProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetDatabaseProperties(NewSqlBaseParser.SetDatabasePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setDatabaseProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetDatabaseProperties(NewSqlBaseParser.SetDatabasePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropDatabase}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropDatabase(NewSqlBaseParser.DropDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropDatabase}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropDatabase(NewSqlBaseParser.DropDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(NewSqlBaseParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(NewSqlBaseParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createHiveTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateHiveTable(NewSqlBaseParser.CreateHiveTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createHiveTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateHiveTable(NewSqlBaseParser.CreateHiveTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createTableLike}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableLike(NewSqlBaseParser.CreateTableLikeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createTableLike}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableLike(NewSqlBaseParser.CreateTableLikeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code analyze}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAnalyze(NewSqlBaseParser.AnalyzeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code analyze}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAnalyze(NewSqlBaseParser.AnalyzeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addTableColumns}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAddTableColumns(NewSqlBaseParser.AddTableColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addTableColumns}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAddTableColumns(NewSqlBaseParser.AddTableColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code renameTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRenameTable(NewSqlBaseParser.RenameTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code renameTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRenameTable(NewSqlBaseParser.RenameTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTableProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTableProperties(NewSqlBaseParser.SetTablePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTableProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTableProperties(NewSqlBaseParser.SetTablePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unsetTableProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUnsetTableProperties(NewSqlBaseParser.UnsetTablePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unsetTableProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUnsetTableProperties(NewSqlBaseParser.UnsetTablePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code changeColumn}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterChangeColumn(NewSqlBaseParser.ChangeColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code changeColumn}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitChangeColumn(NewSqlBaseParser.ChangeColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTableSerDe}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTableSerDe(NewSqlBaseParser.SetTableSerDeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTableSerDe}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTableSerDe(NewSqlBaseParser.SetTableSerDeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addTablePartition}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAddTablePartition(NewSqlBaseParser.AddTablePartitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addTablePartition}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAddTablePartition(NewSqlBaseParser.AddTablePartitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code renameTablePartition}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRenameTablePartition(NewSqlBaseParser.RenameTablePartitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code renameTablePartition}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRenameTablePartition(NewSqlBaseParser.RenameTablePartitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropTablePartitions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropTablePartitions(NewSqlBaseParser.DropTablePartitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropTablePartitions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropTablePartitions(NewSqlBaseParser.DropTablePartitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTableLocation}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTableLocation(NewSqlBaseParser.SetTableLocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTableLocation}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTableLocation(NewSqlBaseParser.SetTableLocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recoverPartitions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRecoverPartitions(NewSqlBaseParser.RecoverPartitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recoverPartitions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRecoverPartitions(NewSqlBaseParser.RecoverPartitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropTable(NewSqlBaseParser.DropTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropTable(NewSqlBaseParser.DropTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createView}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateView(NewSqlBaseParser.CreateViewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createView}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateView(NewSqlBaseParser.CreateViewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createTempViewUsing}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTempViewUsing(NewSqlBaseParser.CreateTempViewUsingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createTempViewUsing}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTempViewUsing(NewSqlBaseParser.CreateTempViewUsingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code alterViewQuery}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAlterViewQuery(NewSqlBaseParser.AlterViewQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code alterViewQuery}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAlterViewQuery(NewSqlBaseParser.AlterViewQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createFunction}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateFunction(NewSqlBaseParser.CreateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createFunction}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateFunction(NewSqlBaseParser.CreateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropFunction}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropFunction(NewSqlBaseParser.DropFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropFunction}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropFunction(NewSqlBaseParser.DropFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code explain}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExplain(NewSqlBaseParser.ExplainContext ctx);
	/**
	 * Exit a parse tree produced by the {@code explain}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExplain(NewSqlBaseParser.ExplainContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showTables}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowTables(NewSqlBaseParser.ShowTablesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showTables}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowTables(NewSqlBaseParser.ShowTablesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowTable(NewSqlBaseParser.ShowTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowTable(NewSqlBaseParser.ShowTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showDatabases}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowDatabases(NewSqlBaseParser.ShowDatabasesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showDatabases}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowDatabases(NewSqlBaseParser.ShowDatabasesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showTblProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowTblProperties(NewSqlBaseParser.ShowTblPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showTblProperties}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowTblProperties(NewSqlBaseParser.ShowTblPropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showColumns}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowColumns(NewSqlBaseParser.ShowColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showColumns}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowColumns(NewSqlBaseParser.ShowColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showPartitions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowPartitions(NewSqlBaseParser.ShowPartitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showPartitions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowPartitions(NewSqlBaseParser.ShowPartitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showFunctions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowFunctions(NewSqlBaseParser.ShowFunctionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showFunctions}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowFunctions(NewSqlBaseParser.ShowFunctionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showCreateTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowCreateTable(NewSqlBaseParser.ShowCreateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showCreateTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowCreateTable(NewSqlBaseParser.ShowCreateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeFunction}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeFunction(NewSqlBaseParser.DescribeFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeFunction}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeFunction(NewSqlBaseParser.DescribeFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeDatabase}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeDatabase(NewSqlBaseParser.DescribeDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeDatabase}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeDatabase(NewSqlBaseParser.DescribeDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeTable(NewSqlBaseParser.DescribeTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeTable(NewSqlBaseParser.DescribeTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code refreshTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRefreshTable(NewSqlBaseParser.RefreshTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code refreshTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRefreshTable(NewSqlBaseParser.RefreshTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code refreshResource}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRefreshResource(NewSqlBaseParser.RefreshResourceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code refreshResource}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRefreshResource(NewSqlBaseParser.RefreshResourceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cacheTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCacheTable(NewSqlBaseParser.CacheTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cacheTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCacheTable(NewSqlBaseParser.CacheTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code uncacheTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUncacheTable(NewSqlBaseParser.UncacheTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code uncacheTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUncacheTable(NewSqlBaseParser.UncacheTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code clearCache}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterClearCache(NewSqlBaseParser.ClearCacheContext ctx);
	/**
	 * Exit a parse tree produced by the {@code clearCache}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitClearCache(NewSqlBaseParser.ClearCacheContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loadData}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLoadData(NewSqlBaseParser.LoadDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loadData}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLoadData(NewSqlBaseParser.LoadDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code truncateTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterTruncateTable(NewSqlBaseParser.TruncateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code truncateTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitTruncateTable(NewSqlBaseParser.TruncateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code repairTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRepairTable(NewSqlBaseParser.RepairTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code repairTable}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRepairTable(NewSqlBaseParser.RepairTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code manageResource}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterManageResource(NewSqlBaseParser.ManageResourceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code manageResource}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitManageResource(NewSqlBaseParser.ManageResourceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code failNativeCommand}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFailNativeCommand(NewSqlBaseParser.FailNativeCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code failNativeCommand}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFailNativeCommand(NewSqlBaseParser.FailNativeCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setConfiguration}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetConfiguration(NewSqlBaseParser.SetConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setConfiguration}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetConfiguration(NewSqlBaseParser.SetConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code resetConfiguration}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterResetConfiguration(NewSqlBaseParser.ResetConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code resetConfiguration}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitResetConfiguration(NewSqlBaseParser.ResetConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createIndexCommand}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateIndexCommand(NewSqlBaseParser.CreateIndexCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createIndexCommand}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateIndexCommand(NewSqlBaseParser.CreateIndexCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showIndexOnRelation}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowIndexOnRelation(NewSqlBaseParser.ShowIndexOnRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showIndexOnRelation}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowIndexOnRelation(NewSqlBaseParser.ShowIndexOnRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropIndexOnRelation}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropIndexOnRelation(NewSqlBaseParser.DropIndexOnRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropIndexOnRelation}
	 * labeled alternative in {@link NewSqlBaseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropIndexOnRelation(NewSqlBaseParser.DropIndexOnRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#indexType}.
	 * @param ctx the parse tree
	 */
	void enterIndexType(NewSqlBaseParser.IndexTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#indexType}.
	 * @param ctx the parse tree
	 */
	void exitIndexType(NewSqlBaseParser.IndexTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#unsupportedHiveNativeCommands}.
	 * @param ctx the parse tree
	 */
	void enterUnsupportedHiveNativeCommands(NewSqlBaseParser.UnsupportedHiveNativeCommandsContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#unsupportedHiveNativeCommands}.
	 * @param ctx the parse tree
	 */
	void exitUnsupportedHiveNativeCommands(NewSqlBaseParser.UnsupportedHiveNativeCommandsContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#createTableHeader}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableHeader(NewSqlBaseParser.CreateTableHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#createTableHeader}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableHeader(NewSqlBaseParser.CreateTableHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#bucketSpec}.
	 * @param ctx the parse tree
	 */
	void enterBucketSpec(NewSqlBaseParser.BucketSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#bucketSpec}.
	 * @param ctx the parse tree
	 */
	void exitBucketSpec(NewSqlBaseParser.BucketSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#skewSpec}.
	 * @param ctx the parse tree
	 */
	void enterSkewSpec(NewSqlBaseParser.SkewSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#skewSpec}.
	 * @param ctx the parse tree
	 */
	void exitSkewSpec(NewSqlBaseParser.SkewSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#locationSpec}.
	 * @param ctx the parse tree
	 */
	void enterLocationSpec(NewSqlBaseParser.LocationSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#locationSpec}.
	 * @param ctx the parse tree
	 */
	void exitLocationSpec(NewSqlBaseParser.LocationSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(NewSqlBaseParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(NewSqlBaseParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertOverwriteTable}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertOverwriteTable(NewSqlBaseParser.InsertOverwriteTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertOverwriteTable}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertOverwriteTable(NewSqlBaseParser.InsertOverwriteTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertIntoTable}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertIntoTable(NewSqlBaseParser.InsertIntoTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertIntoTable}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertIntoTable(NewSqlBaseParser.InsertIntoTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertOverwriteHiveDir}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertOverwriteHiveDir(NewSqlBaseParser.InsertOverwriteHiveDirContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertOverwriteHiveDir}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertOverwriteHiveDir(NewSqlBaseParser.InsertOverwriteHiveDirContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertOverwriteDir}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertOverwriteDir(NewSqlBaseParser.InsertOverwriteDirContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertOverwriteDir}
	 * labeled alternative in {@link NewSqlBaseParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertOverwriteDir(NewSqlBaseParser.InsertOverwriteDirContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#partitionSpecLocation}.
	 * @param ctx the parse tree
	 */
	void enterPartitionSpecLocation(NewSqlBaseParser.PartitionSpecLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#partitionSpecLocation}.
	 * @param ctx the parse tree
	 */
	void exitPartitionSpecLocation(NewSqlBaseParser.PartitionSpecLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#partitionSpec}.
	 * @param ctx the parse tree
	 */
	void enterPartitionSpec(NewSqlBaseParser.PartitionSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#partitionSpec}.
	 * @param ctx the parse tree
	 */
	void exitPartitionSpec(NewSqlBaseParser.PartitionSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#partitionVal}.
	 * @param ctx the parse tree
	 */
	void enterPartitionVal(NewSqlBaseParser.PartitionValContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#partitionVal}.
	 * @param ctx the parse tree
	 */
	void exitPartitionVal(NewSqlBaseParser.PartitionValContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#describeFuncName}.
	 * @param ctx the parse tree
	 */
	void enterDescribeFuncName(NewSqlBaseParser.DescribeFuncNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#describeFuncName}.
	 * @param ctx the parse tree
	 */
	void exitDescribeFuncName(NewSqlBaseParser.DescribeFuncNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#describeColName}.
	 * @param ctx the parse tree
	 */
	void enterDescribeColName(NewSqlBaseParser.DescribeColNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#describeColName}.
	 * @param ctx the parse tree
	 */
	void exitDescribeColName(NewSqlBaseParser.DescribeColNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#ctes}.
	 * @param ctx the parse tree
	 */
	void enterCtes(NewSqlBaseParser.CtesContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#ctes}.
	 * @param ctx the parse tree
	 */
	void exitCtes(NewSqlBaseParser.CtesContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#namedQuery}.
	 * @param ctx the parse tree
	 */
	void enterNamedQuery(NewSqlBaseParser.NamedQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#namedQuery}.
	 * @param ctx the parse tree
	 */
	void exitNamedQuery(NewSqlBaseParser.NamedQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tableProvider}.
	 * @param ctx the parse tree
	 */
	void enterTableProvider(NewSqlBaseParser.TableProviderContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tableProvider}.
	 * @param ctx the parse tree
	 */
	void exitTableProvider(NewSqlBaseParser.TableProviderContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tablePropertyList}.
	 * @param ctx the parse tree
	 */
	void enterTablePropertyList(NewSqlBaseParser.TablePropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tablePropertyList}.
	 * @param ctx the parse tree
	 */
	void exitTablePropertyList(NewSqlBaseParser.TablePropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tableProperty}.
	 * @param ctx the parse tree
	 */
	void enterTableProperty(NewSqlBaseParser.TablePropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tableProperty}.
	 * @param ctx the parse tree
	 */
	void exitTableProperty(NewSqlBaseParser.TablePropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tablePropertyKey}.
	 * @param ctx the parse tree
	 */
	void enterTablePropertyKey(NewSqlBaseParser.TablePropertyKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tablePropertyKey}.
	 * @param ctx the parse tree
	 */
	void exitTablePropertyKey(NewSqlBaseParser.TablePropertyKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tablePropertyValue}.
	 * @param ctx the parse tree
	 */
	void enterTablePropertyValue(NewSqlBaseParser.TablePropertyValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tablePropertyValue}.
	 * @param ctx the parse tree
	 */
	void exitTablePropertyValue(NewSqlBaseParser.TablePropertyValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#constantList}.
	 * @param ctx the parse tree
	 */
	void enterConstantList(NewSqlBaseParser.ConstantListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#constantList}.
	 * @param ctx the parse tree
	 */
	void exitConstantList(NewSqlBaseParser.ConstantListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#nestedConstantList}.
	 * @param ctx the parse tree
	 */
	void enterNestedConstantList(NewSqlBaseParser.NestedConstantListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#nestedConstantList}.
	 * @param ctx the parse tree
	 */
	void exitNestedConstantList(NewSqlBaseParser.NestedConstantListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#createFileFormat}.
	 * @param ctx the parse tree
	 */
	void enterCreateFileFormat(NewSqlBaseParser.CreateFileFormatContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#createFileFormat}.
	 * @param ctx the parse tree
	 */
	void exitCreateFileFormat(NewSqlBaseParser.CreateFileFormatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableFileFormat}
	 * labeled alternative in {@link NewSqlBaseParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void enterTableFileFormat(NewSqlBaseParser.TableFileFormatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableFileFormat}
	 * labeled alternative in {@link NewSqlBaseParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void exitTableFileFormat(NewSqlBaseParser.TableFileFormatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code genericFileFormat}
	 * labeled alternative in {@link NewSqlBaseParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void enterGenericFileFormat(NewSqlBaseParser.GenericFileFormatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code genericFileFormat}
	 * labeled alternative in {@link NewSqlBaseParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void exitGenericFileFormat(NewSqlBaseParser.GenericFileFormatContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#storageHandler}.
	 * @param ctx the parse tree
	 */
	void enterStorageHandler(NewSqlBaseParser.StorageHandlerContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#storageHandler}.
	 * @param ctx the parse tree
	 */
	void exitStorageHandler(NewSqlBaseParser.StorageHandlerContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(NewSqlBaseParser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(NewSqlBaseParser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleInsertQuery}
	 * labeled alternative in {@link NewSqlBaseParser#queryNoWith}.
	 * @param ctx the parse tree
	 */
	void enterSingleInsertQuery(NewSqlBaseParser.SingleInsertQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleInsertQuery}
	 * labeled alternative in {@link NewSqlBaseParser#queryNoWith}.
	 * @param ctx the parse tree
	 */
	void exitSingleInsertQuery(NewSqlBaseParser.SingleInsertQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiInsertQuery}
	 * labeled alternative in {@link NewSqlBaseParser#queryNoWith}.
	 * @param ctx the parse tree
	 */
	void enterMultiInsertQuery(NewSqlBaseParser.MultiInsertQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiInsertQuery}
	 * labeled alternative in {@link NewSqlBaseParser#queryNoWith}.
	 * @param ctx the parse tree
	 */
	void exitMultiInsertQuery(NewSqlBaseParser.MultiInsertQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#queryOrganization}.
	 * @param ctx the parse tree
	 */
	void enterQueryOrganization(NewSqlBaseParser.QueryOrganizationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#queryOrganization}.
	 * @param ctx the parse tree
	 */
	void exitQueryOrganization(NewSqlBaseParser.QueryOrganizationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#multiInsertQueryBody}.
	 * @param ctx the parse tree
	 */
	void enterMultiInsertQueryBody(NewSqlBaseParser.MultiInsertQueryBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#multiInsertQueryBody}.
	 * @param ctx the parse tree
	 */
	void exitMultiInsertQueryBody(NewSqlBaseParser.MultiInsertQueryBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link NewSqlBaseParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void enterQueryTermDefault(NewSqlBaseParser.QueryTermDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link NewSqlBaseParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void exitQueryTermDefault(NewSqlBaseParser.QueryTermDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link NewSqlBaseParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void enterSetOperation(NewSqlBaseParser.SetOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link NewSqlBaseParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void exitSetOperation(NewSqlBaseParser.SetOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterQueryPrimaryDefault(NewSqlBaseParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitQueryPrimaryDefault(NewSqlBaseParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code table}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTable(NewSqlBaseParser.TableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code table}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTable(NewSqlBaseParser.TableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inlineTableDefault1}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterInlineTableDefault1(NewSqlBaseParser.InlineTableDefault1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code inlineTableDefault1}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitInlineTableDefault1(NewSqlBaseParser.InlineTableDefault1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterSubquery(NewSqlBaseParser.SubqueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link NewSqlBaseParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitSubquery(NewSqlBaseParser.SubqueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#sortItem}.
	 * @param ctx the parse tree
	 */
	void enterSortItem(NewSqlBaseParser.SortItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#sortItem}.
	 * @param ctx the parse tree
	 */
	void exitSortItem(NewSqlBaseParser.SortItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void enterQuerySpecification(NewSqlBaseParser.QuerySpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void exitQuerySpecification(NewSqlBaseParser.QuerySpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#aqp}.
	 * @param ctx the parse tree
	 */
	void enterAqp(NewSqlBaseParser.AqpContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#aqp}.
	 * @param ctx the parse tree
	 */
	void exitAqp(NewSqlBaseParser.AqpContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(NewSqlBaseParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(NewSqlBaseParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#confidence}.
	 * @param ctx the parse tree
	 */
	void enterConfidence(NewSqlBaseParser.ConfidenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#confidence}.
	 * @param ctx the parse tree
	 */
	void exitConfidence(NewSqlBaseParser.ConfidenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#hint}.
	 * @param ctx the parse tree
	 */
	void enterHint(NewSqlBaseParser.HintContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#hint}.
	 * @param ctx the parse tree
	 */
	void exitHint(NewSqlBaseParser.HintContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#hintStatement}.
	 * @param ctx the parse tree
	 */
	void enterHintStatement(NewSqlBaseParser.HintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#hintStatement}.
	 * @param ctx the parse tree
	 */
	void exitHintStatement(NewSqlBaseParser.HintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void enterFromClause(NewSqlBaseParser.FromClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void exitFromClause(NewSqlBaseParser.FromClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#aggregation}.
	 * @param ctx the parse tree
	 */
	void enterAggregation(NewSqlBaseParser.AggregationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#aggregation}.
	 * @param ctx the parse tree
	 */
	void exitAggregation(NewSqlBaseParser.AggregationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#groupingSet}.
	 * @param ctx the parse tree
	 */
	void enterGroupingSet(NewSqlBaseParser.GroupingSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#groupingSet}.
	 * @param ctx the parse tree
	 */
	void exitGroupingSet(NewSqlBaseParser.GroupingSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#pivotClause}.
	 * @param ctx the parse tree
	 */
	void enterPivotClause(NewSqlBaseParser.PivotClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#pivotClause}.
	 * @param ctx the parse tree
	 */
	void exitPivotClause(NewSqlBaseParser.PivotClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#pivotColumn}.
	 * @param ctx the parse tree
	 */
	void enterPivotColumn(NewSqlBaseParser.PivotColumnContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#pivotColumn}.
	 * @param ctx the parse tree
	 */
	void exitPivotColumn(NewSqlBaseParser.PivotColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#pivotValue}.
	 * @param ctx the parse tree
	 */
	void enterPivotValue(NewSqlBaseParser.PivotValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#pivotValue}.
	 * @param ctx the parse tree
	 */
	void exitPivotValue(NewSqlBaseParser.PivotValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#lateralView}.
	 * @param ctx the parse tree
	 */
	void enterLateralView(NewSqlBaseParser.LateralViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#lateralView}.
	 * @param ctx the parse tree
	 */
	void exitLateralView(NewSqlBaseParser.LateralViewContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterSetQuantifier(NewSqlBaseParser.SetQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitSetQuantifier(NewSqlBaseParser.SetQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(NewSqlBaseParser.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(NewSqlBaseParser.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#joinRelation}.
	 * @param ctx the parse tree
	 */
	void enterJoinRelation(NewSqlBaseParser.JoinRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#joinRelation}.
	 * @param ctx the parse tree
	 */
	void exitJoinRelation(NewSqlBaseParser.JoinRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#joinType}.
	 * @param ctx the parse tree
	 */
	void enterJoinType(NewSqlBaseParser.JoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#joinType}.
	 * @param ctx the parse tree
	 */
	void exitJoinType(NewSqlBaseParser.JoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#joinCriteria}.
	 * @param ctx the parse tree
	 */
	void enterJoinCriteria(NewSqlBaseParser.JoinCriteriaContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#joinCriteria}.
	 * @param ctx the parse tree
	 */
	void exitJoinCriteria(NewSqlBaseParser.JoinCriteriaContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#sample}.
	 * @param ctx the parse tree
	 */
	void enterSample(NewSqlBaseParser.SampleContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#sample}.
	 * @param ctx the parse tree
	 */
	void exitSample(NewSqlBaseParser.SampleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByPercentile}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByPercentile(NewSqlBaseParser.SampleByPercentileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByPercentile}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByPercentile(NewSqlBaseParser.SampleByPercentileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByRows}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByRows(NewSqlBaseParser.SampleByRowsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByRows}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByRows(NewSqlBaseParser.SampleByRowsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByBucket}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByBucket(NewSqlBaseParser.SampleByBucketContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByBucket}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByBucket(NewSqlBaseParser.SampleByBucketContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByBytes}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByBytes(NewSqlBaseParser.SampleByBytesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByBytes}
	 * labeled alternative in {@link NewSqlBaseParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByBytes(NewSqlBaseParser.SampleByBytesContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(NewSqlBaseParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(NewSqlBaseParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#identifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierSeq(NewSqlBaseParser.IdentifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#identifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierSeq(NewSqlBaseParser.IdentifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#orderedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void enterOrderedIdentifierList(NewSqlBaseParser.OrderedIdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#orderedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void exitOrderedIdentifierList(NewSqlBaseParser.OrderedIdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#orderedIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterOrderedIdentifier(NewSqlBaseParser.OrderedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#orderedIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitOrderedIdentifier(NewSqlBaseParser.OrderedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#identifierCommentList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierCommentList(NewSqlBaseParser.IdentifierCommentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#identifierCommentList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierCommentList(NewSqlBaseParser.IdentifierCommentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#identifierComment}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierComment(NewSqlBaseParser.IdentifierCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#identifierComment}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierComment(NewSqlBaseParser.IdentifierCommentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTableName(NewSqlBaseParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTableName(NewSqlBaseParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aliasedQuery}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterAliasedQuery(NewSqlBaseParser.AliasedQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aliasedQuery}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitAliasedQuery(NewSqlBaseParser.AliasedQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aliasedRelation}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterAliasedRelation(NewSqlBaseParser.AliasedRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aliasedRelation}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitAliasedRelation(NewSqlBaseParser.AliasedRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inlineTableDefault2}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterInlineTableDefault2(NewSqlBaseParser.InlineTableDefault2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code inlineTableDefault2}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitInlineTableDefault2(NewSqlBaseParser.InlineTableDefault2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code tableValuedFunction}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTableValuedFunction(NewSqlBaseParser.TableValuedFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableValuedFunction}
	 * labeled alternative in {@link NewSqlBaseParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTableValuedFunction(NewSqlBaseParser.TableValuedFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#inlineTable}.
	 * @param ctx the parse tree
	 */
	void enterInlineTable(NewSqlBaseParser.InlineTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#inlineTable}.
	 * @param ctx the parse tree
	 */
	void exitInlineTable(NewSqlBaseParser.InlineTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#functionTable}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTable(NewSqlBaseParser.FunctionTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#functionTable}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTable(NewSqlBaseParser.FunctionTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tableAlias}.
	 * @param ctx the parse tree
	 */
	void enterTableAlias(NewSqlBaseParser.TableAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tableAlias}.
	 * @param ctx the parse tree
	 */
	void exitTableAlias(NewSqlBaseParser.TableAliasContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowFormatSerde}
	 * labeled alternative in {@link NewSqlBaseParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void enterRowFormatSerde(NewSqlBaseParser.RowFormatSerdeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowFormatSerde}
	 * labeled alternative in {@link NewSqlBaseParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void exitRowFormatSerde(NewSqlBaseParser.RowFormatSerdeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowFormatDelimited}
	 * labeled alternative in {@link NewSqlBaseParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void enterRowFormatDelimited(NewSqlBaseParser.RowFormatDelimitedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowFormatDelimited}
	 * labeled alternative in {@link NewSqlBaseParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void exitRowFormatDelimited(NewSqlBaseParser.RowFormatDelimitedContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#tableIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterTableIdentifier(NewSqlBaseParser.TableIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#tableIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitTableIdentifier(NewSqlBaseParser.TableIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#functionIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterFunctionIdentifier(NewSqlBaseParser.FunctionIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#functionIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitFunctionIdentifier(NewSqlBaseParser.FunctionIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#namedExpression}.
	 * @param ctx the parse tree
	 */
	void enterNamedExpression(NewSqlBaseParser.NamedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#namedExpression}.
	 * @param ctx the parse tree
	 */
	void exitNamedExpression(NewSqlBaseParser.NamedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#namedExpressionSeq}.
	 * @param ctx the parse tree
	 */
	void enterNamedExpressionSeq(NewSqlBaseParser.NamedExpressionSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#namedExpressionSeq}.
	 * @param ctx the parse tree
	 */
	void exitNamedExpressionSeq(NewSqlBaseParser.NamedExpressionSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(NewSqlBaseParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(NewSqlBaseParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalNot(NewSqlBaseParser.LogicalNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalNot(NewSqlBaseParser.LogicalNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicated(NewSqlBaseParser.PredicatedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicated(NewSqlBaseParser.PredicatedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exists}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterExists(NewSqlBaseParser.ExistsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exists}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitExists(NewSqlBaseParser.ExistsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalBinary}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalBinary(NewSqlBaseParser.LogicalBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalBinary}
	 * labeled alternative in {@link NewSqlBaseParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalBinary(NewSqlBaseParser.LogicalBinaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(NewSqlBaseParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(NewSqlBaseParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpressionDefault(NewSqlBaseParser.ValueExpressionDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpressionDefault(NewSqlBaseParser.ValueExpressionDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterComparison(NewSqlBaseParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitComparison(NewSqlBaseParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticBinary(NewSqlBaseParser.ArithmeticBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticBinary(NewSqlBaseParser.ArithmeticBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticUnary(NewSqlBaseParser.ArithmeticUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link NewSqlBaseParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticUnary(NewSqlBaseParser.ArithmeticUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code struct}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterStruct(NewSqlBaseParser.StructContext ctx);
	/**
	 * Exit a parse tree produced by the {@code struct}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitStruct(NewSqlBaseParser.StructContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterDereference(NewSqlBaseParser.DereferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitDereference(NewSqlBaseParser.DereferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCase(NewSqlBaseParser.SimpleCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCase(NewSqlBaseParser.SimpleCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterColumnReference(NewSqlBaseParser.ColumnReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitColumnReference(NewSqlBaseParser.ColumnReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterRowConstructor(NewSqlBaseParser.RowConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitRowConstructor(NewSqlBaseParser.RowConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code last}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLast(NewSqlBaseParser.LastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code last}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLast(NewSqlBaseParser.LastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code star}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterStar(NewSqlBaseParser.StarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code star}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitStar(NewSqlBaseParser.StarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(NewSqlBaseParser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(NewSqlBaseParser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubqueryExpression(NewSqlBaseParser.SubqueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubqueryExpression(NewSqlBaseParser.SubqueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cast}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCast(NewSqlBaseParser.CastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cast}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCast(NewSqlBaseParser.CastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constantDefault}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantDefault(NewSqlBaseParser.ConstantDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constantDefault}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantDefault(NewSqlBaseParser.ConstantDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambda(NewSqlBaseParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambda(NewSqlBaseParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(NewSqlBaseParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(NewSqlBaseParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code extract}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterExtract(NewSqlBaseParser.ExtractContext ctx);
	/**
	 * Exit a parse tree produced by the {@code extract}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitExtract(NewSqlBaseParser.ExtractContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(NewSqlBaseParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(NewSqlBaseParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSearchedCase(NewSqlBaseParser.SearchedCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSearchedCase(NewSqlBaseParser.SearchedCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code position}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPosition(NewSqlBaseParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code position}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPosition(NewSqlBaseParser.PositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code first}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFirst(NewSqlBaseParser.FirstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code first}
	 * labeled alternative in {@link NewSqlBaseParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFirst(NewSqlBaseParser.FirstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(NewSqlBaseParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(NewSqlBaseParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntervalLiteral(NewSqlBaseParser.IntervalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntervalLiteral(NewSqlBaseParser.IntervalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstructor(NewSqlBaseParser.TypeConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstructor(NewSqlBaseParser.TypeConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(NewSqlBaseParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(NewSqlBaseParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(NewSqlBaseParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(NewSqlBaseParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(NewSqlBaseParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(NewSqlBaseParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(NewSqlBaseParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(NewSqlBaseParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticOperator(NewSqlBaseParser.ArithmeticOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticOperator(NewSqlBaseParser.ArithmeticOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#predicateOperator}.
	 * @param ctx the parse tree
	 */
	void enterPredicateOperator(NewSqlBaseParser.PredicateOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#predicateOperator}.
	 * @param ctx the parse tree
	 */
	void exitPredicateOperator(NewSqlBaseParser.PredicateOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(NewSqlBaseParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(NewSqlBaseParser.BooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(NewSqlBaseParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(NewSqlBaseParser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#intervalField}.
	 * @param ctx the parse tree
	 */
	void enterIntervalField(NewSqlBaseParser.IntervalFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#intervalField}.
	 * @param ctx the parse tree
	 */
	void exitIntervalField(NewSqlBaseParser.IntervalFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#intervalValue}.
	 * @param ctx the parse tree
	 */
	void enterIntervalValue(NewSqlBaseParser.IntervalValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#intervalValue}.
	 * @param ctx the parse tree
	 */
	void exitIntervalValue(NewSqlBaseParser.IntervalValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#colPosition}.
	 * @param ctx the parse tree
	 */
	void enterColPosition(NewSqlBaseParser.ColPositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#colPosition}.
	 * @param ctx the parse tree
	 */
	void exitColPosition(NewSqlBaseParser.ColPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code complexDataType}
	 * labeled alternative in {@link NewSqlBaseParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterComplexDataType(NewSqlBaseParser.ComplexDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code complexDataType}
	 * labeled alternative in {@link NewSqlBaseParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitComplexDataType(NewSqlBaseParser.ComplexDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primitiveDataType}
	 * labeled alternative in {@link NewSqlBaseParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveDataType(NewSqlBaseParser.PrimitiveDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primitiveDataType}
	 * labeled alternative in {@link NewSqlBaseParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveDataType(NewSqlBaseParser.PrimitiveDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#colTypeList}.
	 * @param ctx the parse tree
	 */
	void enterColTypeList(NewSqlBaseParser.ColTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#colTypeList}.
	 * @param ctx the parse tree
	 */
	void exitColTypeList(NewSqlBaseParser.ColTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#colType}.
	 * @param ctx the parse tree
	 */
	void enterColType(NewSqlBaseParser.ColTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#colType}.
	 * @param ctx the parse tree
	 */
	void exitColType(NewSqlBaseParser.ColTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#complexColTypeList}.
	 * @param ctx the parse tree
	 */
	void enterComplexColTypeList(NewSqlBaseParser.ComplexColTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#complexColTypeList}.
	 * @param ctx the parse tree
	 */
	void exitComplexColTypeList(NewSqlBaseParser.ComplexColTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#complexColType}.
	 * @param ctx the parse tree
	 */
	void enterComplexColType(NewSqlBaseParser.ComplexColTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#complexColType}.
	 * @param ctx the parse tree
	 */
	void exitComplexColType(NewSqlBaseParser.ComplexColTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void enterWhenClause(NewSqlBaseParser.WhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void exitWhenClause(NewSqlBaseParser.WhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#windows}.
	 * @param ctx the parse tree
	 */
	void enterWindows(NewSqlBaseParser.WindowsContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#windows}.
	 * @param ctx the parse tree
	 */
	void exitWindows(NewSqlBaseParser.WindowsContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#namedWindow}.
	 * @param ctx the parse tree
	 */
	void enterNamedWindow(NewSqlBaseParser.NamedWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#namedWindow}.
	 * @param ctx the parse tree
	 */
	void exitNamedWindow(NewSqlBaseParser.NamedWindowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code windowRef}
	 * labeled alternative in {@link NewSqlBaseParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void enterWindowRef(NewSqlBaseParser.WindowRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code windowRef}
	 * labeled alternative in {@link NewSqlBaseParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void exitWindowRef(NewSqlBaseParser.WindowRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code windowDef}
	 * labeled alternative in {@link NewSqlBaseParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void enterWindowDef(NewSqlBaseParser.WindowDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code windowDef}
	 * labeled alternative in {@link NewSqlBaseParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void exitWindowDef(NewSqlBaseParser.WindowDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#windowFrame}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrame(NewSqlBaseParser.WindowFrameContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#windowFrame}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrame(NewSqlBaseParser.WindowFrameContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void enterFrameBound(NewSqlBaseParser.FrameBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void exitFrameBound(NewSqlBaseParser.FrameBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(NewSqlBaseParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(NewSqlBaseParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(NewSqlBaseParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(NewSqlBaseParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link NewSqlBaseParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterUnquotedIdentifier(NewSqlBaseParser.UnquotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link NewSqlBaseParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitUnquotedIdentifier(NewSqlBaseParser.UnquotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code quotedIdentifierAlternative}
	 * labeled alternative in {@link NewSqlBaseParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterQuotedIdentifierAlternative(NewSqlBaseParser.QuotedIdentifierAlternativeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quotedIdentifierAlternative}
	 * labeled alternative in {@link NewSqlBaseParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitQuotedIdentifierAlternative(NewSqlBaseParser.QuotedIdentifierAlternativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#quotedIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterQuotedIdentifier(NewSqlBaseParser.QuotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#quotedIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitQuotedIdentifier(NewSqlBaseParser.QuotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(NewSqlBaseParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(NewSqlBaseParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(NewSqlBaseParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(NewSqlBaseParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bigIntLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterBigIntLiteral(NewSqlBaseParser.BigIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bigIntLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitBigIntLiteral(NewSqlBaseParser.BigIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code smallIntLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterSmallIntLiteral(NewSqlBaseParser.SmallIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code smallIntLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitSmallIntLiteral(NewSqlBaseParser.SmallIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tinyIntLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterTinyIntLiteral(NewSqlBaseParser.TinyIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tinyIntLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitTinyIntLiteral(NewSqlBaseParser.TinyIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterDoubleLiteral(NewSqlBaseParser.DoubleLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitDoubleLiteral(NewSqlBaseParser.DoubleLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bigDecimalLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void enterBigDecimalLiteral(NewSqlBaseParser.BigDecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bigDecimalLiteral}
	 * labeled alternative in {@link NewSqlBaseParser#number}.
	 * @param ctx the parse tree
	 */
	void exitBigDecimalLiteral(NewSqlBaseParser.BigDecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link NewSqlBaseParser#nonReserved}.
	 * @param ctx the parse tree
	 */
	void enterNonReserved(NewSqlBaseParser.NonReservedContext ctx);
	/**
	 * Exit a parse tree produced by {@link NewSqlBaseParser#nonReserved}.
	 * @param ctx the parse tree
	 */
	void exitNonReserved(NewSqlBaseParser.NonReservedContext ctx);
}