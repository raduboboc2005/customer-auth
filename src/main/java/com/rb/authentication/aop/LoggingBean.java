package com.rb.authentication.aop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoggingBean {
    private ApiType apiType;
    private String className;
    private Status status;
    private String statusCode;
    private String method;
    private Object[] arguments;
    private long durationMs;
    private String action;
    private String detailMessage;
    private String stackTrace;
    private Object returnValue;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("apiType=\"").append(apiType)
                .append("\", className=\"").append(className)
                .append("\", method=\"").append(method);
        if (action != null) {
            sb.append("\", action=\"").append(action);
        }
        if (detailMessage != null) {
            sb.append("\", detailMessage=\"").append(detailMessage.toString());
        }
        if(status != null){
            sb.append("\", status=\"").append(status.toString());
        }
        if(statusCode != null){
            sb.append("\", statusCode=\"").append(statusCode);
        }
        sb.append("\", durationMs=\"").append(durationMs);
        if(stackTrace != null) {
            sb.append("\", stackTrace=\"").append(stackTrace);
        }
        if (returnValue != null) {
            sb.append("\", return=\"").append(returnValue.toString());
        }
        sb.append("\" }");
        return sb.toString();

    }

    public enum ApiType {
        DB("DATABASE"),
        CONTROLLER("CONTROLLER");

        private final String type;

        ApiType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum Status {SUCCESS, FAILURE, DEBUGGING, WARNING}
}
