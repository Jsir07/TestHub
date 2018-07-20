package com.jxq.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.io.InputStream;

/**
 * JsonSchema工具类
 */
public class JsonSchemaUtils {
    /**
     * 从指定路径读取Schema信息
     *
     * @param filePath Schema路径
     * @return JsonNode型Schema
     * @throws IOException 抛出IO异常
     */
    private static JsonNode readJSONfile(String filePath) throws IOException {
        InputStream stream = JsonSchemaUtils.class.getClassLoader().getResourceAsStream(filePath);
        return new JsonNodeReader().fromInputStream(stream);
    }


    /**
     * 将Json的String型转JsonNode类型
     *
     * @param str 需要转换的Json String对象
     * @return 转换JsonNode对象
     * @throws IOException 抛出IO异常
     */
    private static JsonNode readJSONStr(String str) throws IOException {
        return new ObjectMapper().readTree(str);
    }

    /**
     * 将需要验证的JsonNode 与 JsonSchema标准对象 进行比较
     *
     * @param schema schema标准对象
     * @param data   需要比对的Schema对象
     */
    private static void assertJsonSchema(JsonNode schema, JsonNode data) {
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
        if (!report.isSuccess()) {
            for (ProcessingMessage aReport : report) {
                Reporter.log(aReport.getMessage(), true);
            }
        }
        Assert.assertTrue(report.isSuccess());
    }


    /**
     * 将需要验证的response 与 JsonSchema标准对象 进行比较
     *
     * @param schemaPath JsonSchema标准的路径
     * @param response   需要验证的response
     * @throws IOException 抛出IO异常
     */
    public static void assertResponseJsonSchema(String schemaPath, String response) throws IOException {
        JsonNode jsonSchema = readJSONfile(schemaPath);
        JsonNode responseJN = readJSONStr(response);
        assertJsonSchema(jsonSchema, responseJN);
    }
}
