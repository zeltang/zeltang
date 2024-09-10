package com.zeltang.it.es;

import com.zeltang.it.client.EsClient;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import java.io.IOException;

public class TestIndexManage {
    private RestHighLevelClient client = EsClient.getClient();

    //索引库名字
    private String index = "person";

    /**
     * 创建分片, 索引以及索引结构
     * @throws Exception
     */
    @Test
    public void testCreateIndex() throws Exception {
        //1. 创建主分片和备份分片
        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1);

        //2. 创建索引结构
        XContentBuilder mapping = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("name")
                .field("type", "text")
                .endObject()
                .startObject("age")
                .field("type", "integer")
                .endObject()
                .startObject("birthday")
                .field("type", "date")
                .field("format", "yyyy-MM-dd")
                .endObject()
                .endObject()
                .endObject();

        //3. 将分片和索引结构封装成一个Request对象
        CreateIndexRequest request = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(mapping);

        //4. 发送请求执行创建并返回响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("========" + response);
    }



    /**
     * 查看索引是否存在
     * @throws IOException
     */
    @Test
    public void testIndexExists() throws IOException {
        //1. 准备Get请求对象
        GetAliasesRequest request = new GetAliasesRequest();
        //将索引名字加入到请求对象中
        request.indices(index);

        //2. 通过client去操作
        boolean exists = client.indices().existsAlias(request, RequestOptions.DEFAULT);

        //3. 输出
        System.out.println(exists);
    }


    /**
     * 删除索引
     * @throws Exception
     */
    @Test
    public void testDeleteIndex() throws Exception{
        //1. 准备删除索引请求对象
        DeleteIndexRequest request = new DeleteIndexRequest();
        //将需要删除的索引名字加入到请求对象中
        request.indices(index);

        //2. 通过client对象执行
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);

        //3. 获取返回结果
        System.out.println(delete.isAcknowledged());
    }


}
