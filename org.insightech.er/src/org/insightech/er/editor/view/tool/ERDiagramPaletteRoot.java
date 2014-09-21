package org.insightech.er.editor.view.tool;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.insightech.er.Activator;
import org.insightech.er.ImageKey;
import org.insightech.er.ResourceString;
import org.insightech.er.editor.model.diagram_contents.element.connection.CommentConnection;
import org.insightech.er.editor.model.diagram_contents.element.connection.RelatedTable;
import org.insightech.er.editor.model.diagram_contents.element.connection.Relation;
import org.insightech.er.editor.model.diagram_contents.element.connection.RelationByExistingColumns;
import org.insightech.er.editor.model.diagram_contents.element.connection.SelfRelation;
import org.insightech.er.editor.model.diagram_contents.element.node.category.Category;
import org.insightech.er.editor.model.diagram_contents.element.node.note.Note;
import org.insightech.er.editor.model.diagram_contents.element.node.table.ERTable;
import org.insightech.er.editor.model.diagram_contents.element.node.view.View;

public class ERDiagramPaletteRoot extends PaletteRoot {

	public ERDiagramPaletteRoot() {
		PaletteGroup group = new PaletteGroup("");

		// when tooltip equals to label, tooltip is not displayed.
		PanningSelectionToolEntry selectionTool = new PanningSelectionToolEntry(
				ResourceString.getResourceString("label.select"));
		selectionTool.setToolClass(MovablePanningSelectionTool.class);
		selectionTool
				.setLargeIcon(Activator.getImageDescriptor(ImageKey.ARROW));
		selectionTool
				.setSmallIcon(Activator.getImageDescriptor(ImageKey.ARROW));

		group.add(selectionTool);
		// group.add(new MarqueeToolEntry());

		group.add(new CreationToolEntry(ResourceString
				.getResourceString("label.table"), ResourceString
				.getResourceString("label.create.table"), new SimpleFactory(
				ERTable.class), Activator
				.getImageDescriptor(ImageKey.TABLE_NEW), Activator
				.getImageDescriptor(ImageKey.TABLE_NEW)));

		group.add(new CreationToolEntry(ResourceString
				.getResourceString("label.view"), ResourceString
				.getResourceString("label.create.view"), new SimpleFactory(
				View.class), Activator.getImageDescriptor(ImageKey.VIEW),
				Activator.getImageDescriptor(ImageKey.VIEW)));

		ConnectionCreationToolEntry oneToManyTool = new ConnectionCreationToolEntry(
				ResourceString.getResourceString("label.relation.one.to.many"),
				ResourceString
						.getResourceString("label.create.relation.one.to.many"),
				new SimpleFactory(Relation.class), Activator
						.getImageDescriptor(ImageKey.RELATION_1_N), Activator
						.getImageDescriptor(ImageKey.RELATION_1_N));
		oneToManyTool.setToolClass(RelationCreationTool.class);
		group.add(oneToManyTool);

		ConnectionCreationToolEntry relationByExistingTool = new ConnectionCreationToolEntry(
				ResourceString
						.getResourceString("label.relation.by.existing.columns"),
				ResourceString
						.getResourceString("label.create.relation.by.existing.columns"),
				new SimpleFactory(RelationByExistingColumns.class), Activator
						.getImageDescriptor(ImageKey.RELATION_1_N), Activator
						.getImageDescriptor(ImageKey.RELATION_1_N));
		relationByExistingTool
				.setToolClass(RelationByExistingColumnsCreationTool.class);
		group.add(relationByExistingTool);

		ConnectionCreationToolEntry manyToManyTool = new ConnectionCreationToolEntry(
				ResourceString.getResourceString("label.relation.many.to.many"),
				ResourceString
						.getResourceString("label.create.relation.many.to.many"),
				new SimpleFactory(RelatedTable.class), Activator
						.getImageDescriptor(ImageKey.RELATION_N_N), Activator
						.getImageDescriptor(ImageKey.RELATION_N_N));
		manyToManyTool.setToolClass(RelatedTableCreationTool.class);
		group.add(manyToManyTool);

		ConnectionCreationToolEntry selfRelationTool = new ConnectionCreationToolEntry(
				ResourceString.getResourceString("label.relation.self"),
				ResourceString.getResourceString("label.create.relation.self"),
				new SimpleFactory(SelfRelation.class),
				Activator.getImageDescriptor(ImageKey.RELATION_SELF),
				Activator.getImageDescriptor(ImageKey.RELATION_SELF));
		selfRelationTool.setToolClass(SelfRelationCreationTool.class);
		group.add(selfRelationTool);

		group.add(new PaletteSeparator());

		CreationToolEntry noteTool = new CreationToolEntry(
				ResourceString.getResourceString("label.note"),
				ResourceString.getResourceString("label.create.note"),
				new SimpleFactory(Note.class),
				Activator.getImageDescriptor(ImageKey.NOTE),
				Activator.getImageDescriptor(ImageKey.NOTE));
		group.add(noteTool);

		ConnectionCreationToolEntry commentConnectionTool = new ConnectionCreationToolEntry(
				ResourceString.getResourceString("label.relation.note"),
				ResourceString.getResourceString("label.create.relation.note"),
				new SimpleFactory(CommentConnection.class),
				Activator.getImageDescriptor(ImageKey.COMMENT_CONNECTION),
				Activator.getImageDescriptor(ImageKey.COMMENT_CONNECTION));
		group.add(commentConnectionTool);

		group.add(new PaletteSeparator());

		group.add(new CreationToolEntry(ResourceString
				.getResourceString("label.category"), ResourceString
				.getResourceString("label.create.category"), new SimpleFactory(
				Category.class), Activator
				.getImageDescriptor(ImageKey.CATEGORY), Activator
				.getImageDescriptor(ImageKey.CATEGORY)));

		group.add(new PaletteSeparator());

		group.add(new InsertImageTool());

		this.add(group);

		this.setDefaultEntry(selectionTool);
	}

}
