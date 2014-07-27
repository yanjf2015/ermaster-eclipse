package org.insightech.er.editor.model.dbexport.image;

import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.insightech.er.editor.model.diagram_contents.element.node.Location;
import org.insightech.er.editor.model.diagram_contents.element.node.table.TableView;

public class ImageInfo {

	private Image image;

	private Map<TableView, Location> tableLocationMap;

	private int format;

	private String path;

	public ImageInfo(Image image, Map<TableView, Location> tableLocationMap) {
		this.image = image;
		this.tableLocationMap = tableLocationMap;
	}

	public void dispose() {
		this.image.dispose();
	}

	public Image getImage() {
		return image;
	}

	public Map<TableView, Location> getTableLocationMap() {
		return tableLocationMap;
	}

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
