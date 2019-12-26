package alankzh.blog.base.api;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseResponse<T> {
    private int code = ResultCode.SUCCESS.getCode();
    private String msg;

    private T data;
}
