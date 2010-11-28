package org.insightech.er.db.impl.db2;

import org.insightech.er.db.impl.db2.tablespace.DB2TablespaceProperties;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.model.dbexport.ddl.DDLCreator;
import org.insightech.er.editor.model.diagram_contents.element.node.table.column.NormalColumn;
import org.insightech.er.editor.model.diagram_contents.not_element.sequence.Sequence;
import org.insightech.er.editor.model.diagram_contents.not_element.tablespace.Tablespace;
import org.insightech.er.util.Check;

public class DB2DDLCreator extends DDLCreator {

	public DB2DDLCreator(ERDiagram diagram, boolean semicolon) {
		super(diagram, semicolon);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getColulmnDDL(NormalColumn normalColumn) {
		StringBuffer ddl = new StringBuffer();

		ddl.append(super.getColulmnDDL(normalColumn));

		if (normalColumn.isAutoIncrement()) {
			ddl.append(" GENERATED ALWAYS AS IDENTITY ");

			Sequence sequence = normalColumn.getAutoIncrementSetting();

			if (sequence.getIncrement() != null || sequence.getStart() != null) {
				ddl.append("(START WITH ");
				if (sequence.getStart() != null) {
					ddl.append(sequence.getStart());

				} else {
					ddl.append("1");
				}

				if (sequence.getIncrement() != null) {
					ddl.append(", INCREMENT BY ");
					ddl.append(sequence.getIncrement());
				}

				ddl.append(")");
			}
		}

		return ddl.toString();
	}
	
	@Override
	protected String getDDL(Tablespace tablespace) {
		DB2TablespaceProperties tablespaceProperties = (DB2TablespaceProperties) tablespace
				.getProperties(this.environment, this.getDiagram());

		StringBuffer ddl = new StringBuffer();

		ddl.append("CREATE ");
		if (!Check.isEmpty(tablespaceProperties.getType())) {
			ddl.append(tablespaceProperties.getType());
			ddl.append(" ");
		}

		ddl.append("TABLESPACE ");
		ddl.append(filter(tablespace.getName()));
		ddl.append("\r\n");

		if (!Check.isEmpty(tablespaceProperties.getPageSize())) {
			ddl.append(" PAGESIZE ");
			ddl.append(tablespaceProperties.getPageSize());
			ddl.append("\r\n");
		}

		ddl.append(" MANAGED BY ");
		ddl.append(tablespaceProperties.getManagedBy());
		ddl.append(" USING(");
		ddl.append(tablespaceProperties.getContainer());
		ddl.append(")\r\n");

		if (!Check.isEmpty(tablespaceProperties.getExtentSize())) {
			ddl.append(" EXTENTSIZE ");
			ddl.append(tablespaceProperties.getExtentSize());
			ddl.append("\r\n");
		}

		if (!Check.isEmpty(tablespaceProperties.getPrefetchSize())) {
			ddl.append(" PREFETCHSIZE ");
			ddl.append(tablespaceProperties.getPrefetchSize());
			ddl.append("\r\n");
		}

		if (!Check.isEmpty(tablespaceProperties.getBufferPoolName())) {
			ddl.append(" BUFFERPOOL ");
			ddl.append(tablespaceProperties.getBufferPoolName());
			ddl.append("\r\n");
		}

		if (this.semicolon) {
			ddl.append(";");
		}

		return ddl.toString();
	}
}
