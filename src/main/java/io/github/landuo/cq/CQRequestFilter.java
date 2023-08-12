package io.github.landuo.cq;

import cn.hutool.extra.spring.SpringUtil;
import io.github.landuo.cq.msg.common.BaseMsg;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author accidia
 */
@Slf4j
public class CQRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 读取请求体
            StringBuilder requestBody = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }
            // 在此处处理请求体数据
            String requestData = requestBody.toString();
            CqContext cqContext = SpringUtil.getBean(CqContext.class);
            BaseMsg msg = cqContext.getMsg(requestData);
            CqContext.ORIGIN_MSG.set(msg);
            HttpServletRequest wrappedRequest = new PostBodyServletRequestWrapper(request, requestBody.toString());
            // 执行过滤链
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            CqContext.ORIGIN_MSG.remove();
        }
    }

    private static class PostBodyServletRequestWrapper extends HttpServletRequestWrapper {
        private final String requestBody;

        public PostBodyServletRequestWrapper(HttpServletRequest request, String requestBody) {
            super(request);
            this.requestBody = requestBody;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            byte[] bytes = requestBody.getBytes();
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }
    }
}
