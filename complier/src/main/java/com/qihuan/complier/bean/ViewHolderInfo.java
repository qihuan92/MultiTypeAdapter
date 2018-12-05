package com.qihuan.complier.bean;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * ViewHolderInfo
 *
 * @author qi
 * @date 2018/11/30
 */
public class ViewHolderInfo {
    private int layoutId;
    private Element element;
    private TypeMirror dataClassTypeMirror;
    private TypeMirror viewHolderTypeMirror;

    public ViewHolderInfo() {
    }

    public ViewHolderInfo(int layoutId, Element element, TypeMirror dataClassTypeMirror, TypeMirror viewHolderTypeMirror) {
        this.layoutId = layoutId;
        this.element = element;
        this.dataClassTypeMirror = dataClassTypeMirror;
        this.viewHolderTypeMirror = viewHolderTypeMirror;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public ViewHolderInfo setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public Element getElement() {
        return element;
    }

    public ViewHolderInfo setElement(Element element) {
        this.element = element;
        return this;
    }

    public TypeMirror getDataClassTypeMirror() {
        return dataClassTypeMirror;
    }

    public ViewHolderInfo setDataClassTypeMirror(TypeMirror dataClassTypeMirror) {
        this.dataClassTypeMirror = dataClassTypeMirror;
        return this;
    }

    public TypeMirror getViewHolderTypeMirror() {
        return viewHolderTypeMirror;
    }

    public ViewHolderInfo setViewHolderTypeMirror(TypeMirror viewHolderTypeMirror) {
        this.viewHolderTypeMirror = viewHolderTypeMirror;
        return this;
    }
}
