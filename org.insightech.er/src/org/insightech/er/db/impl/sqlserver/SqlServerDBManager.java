package org.insightech.er.db.impl.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.insightech.er.db.DBManagerBase;
import org.insightech.er.db.impl.sqlserver.tablespace.SqlServerTablespaceProperties;
import org.insightech.er.db.sqltype.SqlTypeManager;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.model.dbexport.db.PreTableExportManager;
import org.insightech.er.editor.model.dbexport.ddl.DDLCreator;
import org.insightech.er.editor.model.dbimport.ImportFromDBManager;
import org.insightech.er.editor.model.dbimport.PreImportFromDBManager;
import org.insightech.er.editor.model.diagram_contents.element.node.table.ERTable;
import org.insightech.er.editor.model.diagram_contents.element.node.table.properties.TableProperties;
import org.insightech.er.editor.model.diagram_contents.not_element.tablespace.TablespaceProperties;

public class SqlServerDBManager extends DBManagerBase {

	public static final String ID = "SQLServer";

	public String getId() {
		return ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDriverClassName() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getURL() {
		return "jdbc:sqlserver://<SERVER NAME>\\<DB NAME>:<PORT>";
	}

	public int getDefaultPort() {
		return 1433;
	}

	public SqlTypeManager getSqlTypeManager() {
		return new SqlServerSqlTypeManager();
	}

	public TableProperties createTableProperties(TableProperties tableProperties) {
		if (tableProperties != null
				&& tableProperties instanceof SqlServerTableProperties) {
			return tableProperties;
		}

		return new SqlServerTableProperties();
	}

	public DDLCreator getDDLCreator(ERDiagram diagram, boolean semicolon) {
		return new SqlServerDDLCreator(diagram, semicolon);
	}

	public List<String> getIndexTypeList(ERTable table) {
		List<String> list = new ArrayList<String>();

		list.add("BTREE");

		return list;
	}

	@Override
	protected int[] getSupportItems() {
		return new int[] { SUPPORT_AUTO_INCREMENT,
				SUPPORT_AUTO_INCREMENT_SETTING, SUPPORT_SCHEMA };
	}

	public ImportFromDBManager getTableImportManager() {
		return new SqlServerTableImportManager();
	}

	public PreImportFromDBManager getPreTableImportManager() {
		return new SqlServerPreTableImportManager();
	}

	public PreTableExportManager getPreTableExportManager() {
		return new SqlServerPreTableExportManager();
	}

	public TablespaceProperties createTablespaceProperties() {
		return new SqlServerTablespaceProperties();
	}

	public TablespaceProperties checkTablespaceProperties(
			TablespaceProperties tablespaceProperties) {

		if (!(tablespaceProperties instanceof SqlServerTablespaceProperties)) {
			return new SqlServerTablespaceProperties();
		}

		return tablespaceProperties;
	}

	public String[] getCurrentTimeValue() {
		return new String[] { "GETDATE()", "CURRENT_TIMESTAMP" };
	}
}
