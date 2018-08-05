package com.ace.cms.utils;

import com.ace.cms.vo.Response;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WriterResponseUtil {
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public static void writerResponse(HttpServletResponse httpServletResponse, Response<Map<String, Object>> result) throws IOException {
        httpServletResponse.setContentType("application/json;charset=" + UTF8);
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.print(JSONObject.toJSONString(result));
            httpServletResponse.flushBuffer();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
