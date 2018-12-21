package com.mycompany.gaviao.validate;

public abstract interface IValidate {
    public boolean validatePost(Object object);
    public boolean validateGet();
    public boolean validatePut(Object object);
    public boolean validateDelete();

}
