package X_X.view;

public @interface Logic {
    int action();
    String[] params();//参数
    ParamsType[] paramsType();//参数类型
    boolean[] paramsCanNull();//参数是否可以为空
}
