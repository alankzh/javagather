package alankzh.blog.base.api;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class BaseResponse<T> {
    private int code = ResultCode.SUCCESS.getCode();
    private String msg;

    private T data;

    public static BaseResponse buildWithResultCode(@NotNull ResultCode resultCode){
        return new BaseResponse()
                .setCode(resultCode.getCode())
                .setMsg(resultCode.getMsg());
    }

    public static BaseResponse buildWithResultCodeAndMsg(@NotNull ResultCode resultCode,@NotNull String msg){
        return new BaseResponse()
                .setCode(resultCode.getCode())
                .setMsg(msg);
    }
}
