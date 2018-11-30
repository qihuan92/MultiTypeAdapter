package com.qihuan.complier.bean;

import javax.lang.model.type.TypeMirror;

/**
 * ViewHolderInfoBean
 *
 * @author qi
 * @date 2018/11/30
 */
public class ViewHolderInfoBean {
    private int layoutId;
    private TypeMirror dataClassTypeMirror;
    private TypeMirror viewHolderTypeMirror;

    public ViewHolderInfoBean() {
    }

    public ViewHolderInfoBean(int layoutId, TypeMirror dataClassTypeMirror, TypeMirror viewHolderTypeMirror) {
        this.layoutId = layoutId;
        this.dataClassTypeMirror = dataClassTypeMirror;
        this.viewHolderTypeMirror = viewHolderTypeMirror;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public ViewHolderInfoBean setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public TypeMirror getDataClassTypeMirror() {
        return dataClassTypeMirror;
    }

    public ViewHolderInfoBean setDataClassTypeMirror(TypeMirror dataClassTypeMirror) {
        this.dataClassTypeMirror = dataClassTypeMirror;
        return this;
    }

    public TypeMirror getViewHolderTypeMirror() {
        return viewHolderTypeMirror;
    }

    public ViewHolderInfoBean setViewHolderTypeMirror(TypeMirror viewHolderTypeMirror) {
        this.viewHolderTypeMirror = viewHolderTypeMirror;
        return this;
    }
}
