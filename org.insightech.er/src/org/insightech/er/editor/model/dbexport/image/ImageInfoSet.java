package org.insightech.er.editor.model.dbexport.image;

import java.util.HashMap;
import java.util.Map;

import org.insightech.er.editor.model.diagram_contents.element.node.category.Category;

public class ImageInfoSet {

	private ImageInfo diagramImageInfo;

	private Map<Category, ImageInfo> categoryImageInfoMap;

	public ImageInfoSet(ImageInfo diagramImageInfo) {
		this.diagramImageInfo = diagramImageInfo;
		this.categoryImageInfoMap = new HashMap<Category, ImageInfo>();
	}

	public ImageInfo getDiagramImageInfo() {
		return this.diagramImageInfo;
	}

	public void addImageInfo(Category category, ImageInfo imageInfo) {
		this.categoryImageInfoMap.put(category, imageInfo);
	}

	public ImageInfo getImageInfo(Category category) {
		return this.categoryImageInfoMap.get(category);
	}

}
