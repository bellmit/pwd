package rongshanghui.tastebychance.com.rongshanghui.util.network;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/12 17:33
 * 修改人：Administrator
 * 修改时间：2017/12/12 17:33
 * 修改备注：
 */

public abstract class MyCallBack<T> {
    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public MyCallBack() {
        System.out.println("----------ddd");
        mType = getSuperclassTypeParameter(getClass());
    }

    public abstract void onLoadingBefore(Request request);

    //第二个参数为传入的泛型，也就是说返回的结果直接就是我们需要的类型
    public abstract void onSuccess(Response response, T result);

    public abstract void onFailure(Request request, Exception e);

    public abstract void onError(Response response);
}
