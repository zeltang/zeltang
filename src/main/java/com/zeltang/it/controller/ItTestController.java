package com.zeltang.it.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zeltang.it.tools.chain.ReceiptHandleChain;
import com.zeltang.it.tools.chain.entity.ReceiptVo;
import com.zeltang.it.tools.chain2.DefaultHandler;
import com.zeltang.it.entity.DataDicValueVo;
import com.zeltang.it.entity.TestEntity;
import com.zeltang.it.service.MultiTestService;
import com.zeltang.it.utils.TestFileUtil;
import com.zeltang.it.utils.Utils;
import com.zeltang.it.utils.WaterMarkHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//@Slf4j
@Controller
@RequestMapping("/it")
public class ItTestController {

    Logger logger = LoggerFactory.getLogger(ItTestController.class);

    @Autowired
    private MultiTestService multiTestService;

    private ConcurrentHashMap<String, List<Long>> map = new ConcurrentHashMap();



    @Test
    public void test01() {
        String a = "{\"price\": \"350\", \"curreny\": \"CNY\"}\n";

    }


    @Test
    public void test02 () {
        TestEntity te = new TestEntity();
        for (int i = 0; i < 20; i++) {
            te.setCode("" + i);
            System.out.print(te.getCode());
        }
    }

    @Test
    public void test03 () {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            String test = "{\"items\":[{\"deliverAddr\":\"大陆\",\"deliverDays\":\"70\"},{\"deliverAddr\":\"香港\",\"deliverDays\":\"63\"}]}";
            JSONObject jsonObject = JSONObject.parseObject(test);
            String items = jsonObject.getString("items");
            JSONArray objects = JSONArray.parseArray(items);
            Iterator<Object> iterator = objects.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                String s = JSONObject.toJSONString(next);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
//                System.out.print(jsonObject1.getString("deliverAddr")+":"+jsonObject1.getString("deliverDays")+";");
            }
        }
        long end = System.currentTimeMillis();
        System.out.print(end-start);


    }


    @Test
    public void test04 () {
        String a = "0.000001";
        double v = Double.parseDouble(a);
        System.out.println(v);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000000000000000000000000000000");//格式化设置
        System.out.println(decimalFormat.format(v));
        Double price =new BigDecimal(v).divide(new BigDecimal(1.13), 5, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(price);
    }

    @Test
    public void test05 () {
        String b = "指导价（>150天）";
        String a = "CNY未税指导价（>150天）";
        String[] str = b.split("\\（");
        String keyPre =str[0];
        String keyMid = str[1].split("\\）")[0];
        String mid = keyMid.substring(0,keyMid.length()-1);
        String keySuf = b.substring(b.length() - 2, b.length()-1);
        String test = keyPre + "（"+mid+keySuf+"）";
        System.out.println(keyPre);
        System.out.println(mid);
        System.out.println(keySuf);
        System.out.println(test);
    }

    @Test
    public void test06 () {
        BigDecimal a = new BigDecimal(500);
        BigDecimal divide = a.divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_UP);
        System.out.println(divide.stripTrailingZeros().toPlainString());
    }

    @Test
    public void test07 () {
        ArrayList<String> a = new ArrayList<>();
        a.add("1-2");
        a.add("1-3");
        a.add("1-1");
        a.add("u");
        boolean b = a.stream().anyMatch(s -> s.equals("1-1"));
        System.out.println(b);

    }

    @Test
    public void test08 () {
        String date = "Fri Mar 13 00:00:00 CST 2020\n";
        Date d = new Date("Fri Mar 13 00:00:00 CST 2020");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(d);
        System.out.println(format);

    }

    @Test
    public void test09 () {
        String splits = "-11-22-";
        String[] split = splits.split("-");
        for (String s : split) {
            System.out.println(s);
        }
//        System.out.println(split.length > 2 ? split[2] : split[1]);
    }

    @Test
    public void test10 () {
       TestEntity entity = new TestEntity();
       entity.setCode("1");
        Object o = JSONArray.toJSON(entity);
        System.out.println(o);
        System.out.println(entity);
    }

    @Test
    public void test11 () {
        String test = "{\"PRICE\":[{\"currency\":\"CNY\",\"price\":\"2.716\",\"unit\":\"kps\",\"high_price\":\"2.42\"},{\"currency\":\"USD\",\"price\":\"0.385\",\"unit\":\"kps\",\"high_price\":\"0.343\"}]}";
        JSONArray priceArr = JSONObject.parseObject(test).getJSONArray("PRICE");
        System.out.println(priceArr);


    }

    @Test
    public void test12 () {
        String str = "[{\"start\":-5,\"end\":\"\",\"type\":\"P01\",\"name\":\"销售主管审批\"},{\"start\":-10,\"end\":-5,\"type\":\"P04\",\"name\":\"PM审核\"},{\"start\":-20,\"end\":-10,\"type\":\"P05\",\"name\":\"部长审核\"},{\"end\":-20,\"type\":\"P06\",\"name\":\"市场中心长\"}]";
        JSONArray objects = JSONArray.parseArray(str);
        for (int i = 0; i < objects.size(); i++) {
            if (JSONObject.parseObject(objects.get(i).toString()).getString("type").equals("P01")) {
                System.out.println(111);
            }
        }
    }

    @Test
    public void test13 () {
        String num = "12.1";
        boolean numericZidai = isNumericZidai(num);
        System.out.println(numericZidai);
        if (numericZidai) {
            System.out.println(Long.parseLong(num));
        } else {
            System.out.println(Long.parseLong("0"));
        }

    }


    public boolean isNumericZidai(String num) {
        if (StringUtils.isEmpty(num)) {
            return false;
        }
        if (num.length() > 10) {
            return false;
        }
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void test14 () {

        String[] str = new String[1];
        str[0] = "45";
        Long[] longs = JSONObject.parseObject(JSONObject.toJSONString(str), Long[].class);
        System.out.println(longs);


    }

    @Test
    public void test15 () {

        String test = "Windows 1231 =dasd";
//        boolean windows = test.contains("Windows");
//        System.out.println(windows);
        String s = test.toUpperCase();
        System.out.println(s);


    }

    @Test
    public void test16 () {

        String test = "-1-100-64-";
//        boolean windows = test.contains("Windows");
//        System.out.println(windows);
        String s = test.substring(0, test.length()-2);
        System.out.println(test.split("-").length);


    }


    @Test
    public void test17 () {

        String test = "{\"items\":[{\"dic_value\":\"46\",\"dic_value_desc\":\"汽车及工业控制行业2\",\"dic_value_name\":\"C01\",\"priority\":2,\"status\":\"00A\"}," +
                "{\"dic_value\":\"46\",\"dic_value_desc\":\"汽车及工业控制行业1\",\"dic_value_name\":\"C01\",\"priority\":1,\"status\":\"00A\"}," +
                "{\"dic_value\":\"46\",\"dic_value_desc\":\"汽车及工业控制行业3\",\"dic_value_name\":\"C01\",\"priority\":3,\"status\":\"00A\"}]}";
        JSONArray arr = JSONObject.parseObject(test).getJSONArray("items");
        List<DataDicValueVo> dataDicValueVos = arr.toJavaList(DataDicValueVo.class);
        Long aLong = dataDicValueVos.stream().map(s -> s.getPriority()).max(Long::compare).get();
        JSONArray arr1 = new JSONArray();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(arr.get(i).toString());
            jsonObject.put("dic_value_name","C02222");
            arr1.add(jsonObject);


        }
        System.out.println(arr1);
        arr1.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("priority")));
        System.out.println(arr1);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("items", arr1);
//        System.out.println(jsonObject);
//        System.out.println(test);



    }

    @Test
    public void test18 () {
        List<DataDicValueVo> list = new ArrayList<>();
        DataDicValueVo v01 = new DataDicValueVo();
        DataDicValueVo v02 = new DataDicValueVo();
        DataDicValueVo v03 = new DataDicValueVo();
        v01.setStatus("T01");
        v02.setStatus("T02");
        v03.setStatus("T03");
        list.add(v01);
        list.add(v02);
        list.add(v03);
        List<DataDicValueVo> collect = list.stream().filter(s -> !s.getStatus().equals("T01") && !s.getStatus().equals("T02") && !s.getStatus().equals("T03")).collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void test19 () {
        String a = "";
        String b = null;
        System.out.println(a.equals(b));

    }

    @Test
    public void test20 () {
        String test = "{\"STDCOST\":[{\"location\":\"NSL\",\"currency\":\"USD\",\"unit\":\"KPS\",\"cost\":\"0.7619\"}]}";
        JSONArray arr = JSONObject.parseObject(test).getJSONArray("STDCOST");
        System.out.println(arr);
    }

    @Test
    public void test21 () {
        String resMsg = "";
        String miniQty = "-2. 1".trim();
        String supplyStatus = "12".trim();
        // 数据校验，最小起订量miniQty：最多三位小数，且不超过六位整数；供应倍数supplyStatus：1-99之间的整数
        // 匹配整数
        String intRegex = "^-?[0-9]\\d*$";
        // 匹配浮点数
        String floatRegex = "^-?[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
        // 浮点数小数位不超过三位
        String decimalRegex = "^-?\\d+(\\.\\d{1,3})?$";
        if (isEmpty(miniQty) && isEmpty(supplyStatus)) {
            resMsg = "修改内容不能为空";
        }
        if (notEmpty(miniQty)) {
            if (!Pattern.matches(intRegex, miniQty) && !Pattern.matches(floatRegex, miniQty)) {
                resMsg = "最小起订量请输入正确的数字格式";
            } else if (!Pattern.matches(decimalRegex, miniQty)) {
                resMsg = "最小起订量小数位不能超过3位";
            } else {
                float qty = Float.parseFloat(miniQty);
                if (qty<0.001 || qty > 999999) {
                    resMsg = "最小起订量范围0.001-999999";
                }
            }
        }
        if (notEmpty(supplyStatus)) {
            if (!Pattern.matches(intRegex, supplyStatus)) {
                resMsg = "供应倍数请输入正确的数字格式";
            } else {
                int supply = Integer.parseInt(supplyStatus);
                if (supply<1 || supply > 99) {
                    resMsg = "最小起订量范围1-99";
                }
            }
        }
        System.out.println(resMsg);
//        return resMsg;

    }


    @Test
    public void test22 () {
        String test = "{\"items\":[{\"deliverAddr\":\"大陆\",\"deliverDays\":\"70\"},{\"deliverAddr\":\"香港\",\"deliverDays\":\"63\"}]}";
        JSONObject jsonObject = JSONObject.parseObject(test);
        String items = jsonObject.getString("items");
        JSONArray objects = JSONArray.parseArray(items);
        Iterator<Object> iterator = objects.iterator();
        JSONArray os = new JSONArray();
        while (iterator.hasNext()) {
            Object next = iterator.next();

            String s = JSONObject.toJSONString(next);
            JSONObject jsonObject1 = JSONObject.parseObject(s);
            jsonObject1.put("deliverDays", "999");
            os.add(jsonObject1);
            System.out.println(jsonObject1);

        }
        jsonObject.put("items",os);
        System.out.println(JSONObject.toJSONString(jsonObject));





    }

    @Test
    public void test23 () {
        String cycle = "{\"items\":[{\"deliverAddr\":\"大陆\",\"deliverDays\":\"70\"},{\"deliverAddr\":\"香港\",\"deliverDays\":\"63\"}]}";
        String delivery = null;
        String deliveryHk = "30";
        JSONObject jsonObject = new JSONObject();
        if (notEmpty(cycle)) {
            jsonObject = JSONObject.parseObject(cycle);
            String items = jsonObject.getString("items");
            JSONArray objects = JSONArray.parseArray(items);
            Iterator<Object> iterator = objects.iterator();
            JSONArray ja = new JSONArray();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                String s = JSONObject.toJSONString(next);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                if ("大陆".equals(jsonObject1.getString("deliverAddr"))) {
                    if (notEmpty(delivery)) {
                        jsonObject1.put("deliverDays", delivery);
                        ja.add(jsonObject1);
                    } else {
                        jsonObject1.put("deliverDays", jsonObject1.getString("deliverDays"));
                        ja.add(jsonObject1);
                    }
                } else if ("香港".equals(jsonObject1.getString("deliverAddr"))) {
                    if (notEmpty(deliveryHk)) {
                        jsonObject1.put("deliverDays", deliveryHk);
                        ja.add(jsonObject1);
                    } else {
                        jsonObject1.put("deliverDays", jsonObject1.getString("deliverDays"));
                        ja.add(jsonObject1);
                    }
                }

            }
            jsonObject.put("items",ja);
        } else {
            jsonObject = new JSONObject();
            JSONArray ja = new JSONArray();
            if ( notEmpty(delivery)) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("deliverAddr", "大陆");
                jsonObject1.put("deliverDays", delivery);
                ja.add(jsonObject1);
            }
            if (notEmpty(deliveryHk)) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("deliverAddr", "香港");
                jsonObject2.put("deliverDays", deliveryHk);
                ja.add(jsonObject2);
            }
            jsonObject.put("items",ja);
        }
        System.out.println(jsonObject);
    }

    @Test
    public void test24 () {
//        new BigDecimal("1")
        long l = new BigDecimal("2.01100").multiply(new BigDecimal(1000)).longValue();
        System.out.println(l);
    }


    @Test
    public void test25 () {
        String s = new BigDecimal("20.000001").setScale(2, BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();

        System.out.println(s);
    }

    @Test
    public void test26 () {
        float a = Float.parseFloat("99.999");
        if (a<0.001f || a>99.999f) {
            System.out.println(123);
        }
    }

    @Test
    public void test27 () {
        String s = new BigDecimal("0.012").multiply(new BigDecimal(1000)).stripTrailingZeros().toPlainString();
        System.out.println(s);
        
    }


    @Test
    public void test28 () {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type){
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char)(rd.nextInt(25)+65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    uid.append((char)(rd.nextInt(25)+97));
                    break;
                default:
                    break;
            }
        }
        System.out.println(uid.toString());
    }

    @Test
    public void test29 () {
        String s = new BigDecimal(5).divide(new BigDecimal(1000)).toString();
        System.out.println(s);
    }


    @Test
    public void test30 () {

        String fileName = TestFileUtil.getPath() + "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName).inMemory(true).registerWriteHandler(new WaterMarkHandler("商络电子 唐泽龙 2020-10-10"))
                // 这里放入动态头
                .head(head()).sheet("sheet1")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(data());
    }

    @Test
    public void test31 () {
        String resMsg = "";
        String name = "123";
        String tariff = "-1.2";

        // 匹配整数
        String intRegex = "^-?[0-9]\\d*$";
        // 匹配浮点数
        String floatRegex = "^-?[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
        // 浮点数小数位不超过两位
        String decimalRegex = "^-?\\d+(\\.\\d{1,2})?$";
        if (!Pattern.matches(intRegex, tariff) && !Pattern.matches(floatRegex, tariff)) {
            resMsg = name+"请输入正确的数字格式";
        } else if (!Pattern.matches(decimalRegex, tariff)) {
            resMsg = name+"小数位不能超过2位";
        } else {
            float num = Float.parseFloat(tariff);
            if (num < 0f || num > 999999999f) {
                resMsg = name+"范围0-999999999";
            }
        }
        System.out.println(resMsg);
    }

    @Test
    public void test32 () {
        TestEntity testEntity = new TestEntity();
        testEntity.setCode("112");
        if (testEntity.isBol()) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

    }

    @Test
    public void test33 () throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, 7);
        Date d = c.getTime();
        String day = format.format(d);


        Date date = format.parse(day);

        System.out.println("未来七天："+d);
    }

    @Test
    public void test34 () throws ParseException {
        String resMsg = "123";
        String shelfLife = "10000";
        if (isEmpty(resMsg)) {
            return;
        }
        // 匹配整数
        String intRegex = "^-?[0-9]\\d*$";
        if (!Pattern.matches(intRegex, shelfLife)) {
            resMsg = "保质期请输入正确的数字格式";
        } else {
            int num = Integer.parseInt(shelfLife);
            if (num < 0 || num > 9999) {
                resMsg = "保质期范围0-9999";
            }
        }
        System.out.println(resMsg);

    }

    @Test
    public void test35 () {
        JSONObject jsonHead = new JSONObject();
        jsonHead.put("busiType","P01");

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("code","code");
        jsonBody.put("name","");
        jsonBody.put("name","name");
        jsonBody.put("name","name2");

        JSONObject jsonDetail = new JSONObject();
        jsonDetail.put("ext1", "ext1");
        jsonDetail.put("ext2", "ext2");

        List<JSONObject> objects = new ArrayList<>();
        objects.add(jsonDetail);

        jsonBody.put("details", objects);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("head",jsonHead);
        jsonObject.put("body",jsonBody);

        System.out.println(JSONObject.toJSONString(jsonObject));

    }

    @Test
    public void test36 () throws Exception {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String format = df.format(new Date());
        System.out.println(format);


    }

    @Test
    public void test37 () throws Exception {
        String json = "{\"body\":[{\"billCode\":\"GYS\",\"factoryBatchNo\":\"NSL200821000044\",\"inStockDate\":\"2020-08-21\",\"logicLocationCode\":\"NJ\",\"originCountryCode\":\"1\",\"outFactoryDate\":\"2020-04-07\",\"productCode\":\"9308\",\"productionDate\":\"2020-04-07\",\"stockLocationCode\":\"NSL\",\"stockNum\":0.0,\"stockStatus\":\"A\",\"stockUnit\":\"PCS\",\"supplierBatchNo\":\"S8283AP28P\",\"warehousrCode\":\"NJWMS\"},{\"billCode\":\"GYS\",\"factoryBatchNo\":\"NSL200821000044\",\"inStockDate\":\"2020-08-21\",\"logicLocationCode\":\"NJ\",\"originCountryCode\":\"1\",\"outFactoryDate\":\"2020-04-07\",\"productCode\":\"9308\",\"productionDate\":\"2020-04-07\",\"stockLocationCode\":\"NSL\",\"stockNum\":0.0,\"stockStatus\":\"A\",\"stockUnit\":\"PCS\",\"supplierBatchNo\":\"S8283AP28P\",\"warehousrCode\":\"NJWMS\"}],\"head\":{\"busiType\":\"P03\"}}";
        Object body = JSONObject.parseObject(json).get("body");
        JSONArray array = JSONArray.parseArray(body.toString());
        for (int i = 0; i < array.size(); i++) {
            Object o = array.get(i);
            JSONObject jsonObject = JSONObject.parseObject(o.toString());
            Object supplierBatchNo = jsonObject.get("supplierBatchNo");
            logger.info("123");

        }
        logger.info("1234");


    }


    @Test
    public void test38 () throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去120天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 1);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println(d);

    }

    @Test
    public void test39 () throws Exception {
        int size = 2499;
        System.out.println(2499/500);
        System.out.println(2499%500);
        List<Long> tes = Arrays.asList(1L,2L,3L,4L,5L,6L,7L,8L,9L,10L,11L);
        List<Long> list = tes.subList(10, 11);
        System.out.println(list);

    }

    @Test
    public void test40 () throws Exception {
        int num = 29;
        if (num < 30 || num > 9990 || num%30!=0) {
            System.out.println(false);
            return;
        }
        System.out.println(true);

    }

    @Test
    public void test41 () throws Exception {
        String test = "[]";
        List<String> codes = new ArrayList<>();
        JSONArray array = JSONObject.parseArray(test);
        for (int i = 0; i < array.size(); i++) {
            Object o = array.get(i);
            JSONObject jsonObject = JSONObject.parseObject(o.toString());
            String brandCode = jsonObject.getString("brandCode");
            codes.add(brandCode);
        }

        System.out.println(123);

    }

    @Test
    public void test42 () throws Exception {
        List<String> brandCodes = new ArrayList<>();
        JSONArray array = new JSONArray();
        for (String brandCode : brandCodes) {
            JSONObject object = new JSONObject();
            object.put("brandCode", brandCode);
            array.add(object);
        }

        System.out.println(array.toJSONString());

    }

    @Test
    public void test43 () throws Exception {
//        List<Long> list = Arrays.asList(3L, 1L, 5L, 8L);
//        list = list.stream()
//                .sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
//        for (Long i : list) {
//            System.out.println(i);
//        }
        if ((new BigDecimal("0.75").multiply(new BigDecimal("30"))).compareTo(new BigDecimal("30")) > 0) {
            System.out.println(false);
        } else {
            System.out.println(true);
        }

    }

    @Test
    public void test44 () throws Exception {
        List<TestEntity> ts = new ArrayList<>();
        TestEntity t1 = new TestEntity();
        TestEntity t2 = new TestEntity();
        TestEntity t3 = new TestEntity();
        TestEntity t4 = new TestEntity();
        TestEntity t5 = new TestEntity();
        TestEntity t6 = new TestEntity();
        t1.setNum(3L);
        t2.setNum(4L);
        t3.setNum(2L);
        t4.setNum(4L);
        t5.setNum(6L);
        t6.setNum(4L);
        t1.setStatus("B00");
        t2.setStatus("B00");
        t3.setStatus("B04");
        t4.setStatus("B04");
        t5.setStatus("B00");
        t6.setStatus("B04");
        ts.add(t1);
        ts.add(t2);
        ts.add(t3);
        ts.add(t4);
        ts.add(t5);
        ts.add(t6);
        List<TestEntity> collect1 = ts.stream().sorted((a, b) -> b.getNum().compareTo(a.getNum())).collect(Collectors.toList());
        List<TestEntity> collect = ts.stream()
                .filter(s -> ("B00".equals(s.getStatus()) || "B04".equals(s.getStatus())))
                .sorted(Comparator.comparing(TestEntity::getNum).thenComparing(TestEntity::getStatus).reversed()).collect(Collectors.toList());
        System.out.println(1);

    }

    @Test
    public void test45 () throws Exception {
        List<String> list = new ArrayList<>();
        list.add("1333");
        list.add("13331");
        list.add("1323");
        list.add("123");
        list.add("13133");
        list.remove("1231111");
        list.remove("123");
        List<String> list2 = new ArrayList<>();
        list2.add("1333asd");
        list2.add("13331dsd");
        list = list2;

        Map<String, List<String>> map = new HashMap<>();
        List<String> list3 = new ArrayList<>();
        list3.add("12a");
        if (isEmpty(map.get("1"))) {
            map.put("1", list3);
        } else {
            map.get("1").add("123");
        }
        map.get("1").add("1234");

        System.out.println(JSONObject.toJSONString(map));

    }

    @Test
    public void test46 () throws Exception {
        String a = "-2.04";
        String s = new BigDecimal(a).stripTrailingZeros().toPlainString();
        System.out.println(s);
    }

    @Test
    public void test47 () throws Exception {
        String a = "[{\"brandCode\":\"YAGEO\",\"brandName\":\"YAGEO\",\"deriveList\":[{\"brandCode\":\"YAGEO12\",\"brandName\":\"YAGEO12\"},{\"brandCode\":\"YAGEO34\",\"brandName\":\"YAGEO34\"}]},{\"brandCode\":\"TDK\",\"brandName\":\"TDK\",\"deriveList\":[{\"brandCode\":\"TDK12\",\"brandName\":\"TDK12\"},{\"brandCode\":\"TDK34\",\"brandName\":\"TDK34\"}]}]";
        JSONArray array = JSONObject.parseArray(a);



        System.out.println("123");

    }

    @Test
    public void test48 () throws Exception {
        Long l1 = 123L;

        System.out.println(l1>2.31);



    }

    @Test
    public void test49 () throws Exception {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "3");
        map.put("4", "4");
        map.put("4", "2");
        Set<String> collect = map.values().stream().collect(Collectors.toSet());
        System.out.println(JSONObject.toJSONString(collect));


    }

    @Test
    public void test50 () throws Exception {
        List<TestEntity> list = new ArrayList<>();
        List<TestEntity> t = new ArrayList<>();
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        TestEntity entity3 = new TestEntity();
        entity1.setCode("123");
        entity1.setStatus("234");
        entity2.setCode("1232");
        entity2.setStatus("2342");
        entity3.setCode("123");
        entity3.setStatus("234");
        t.add(entity1);
        t.add(entity2);
        t.add(entity3);

        for (TestEntity entity : t) {

            boolean isExist = list.stream().anyMatch(s -> (entity.getCode().equals(s.getCode()) && entity.getStatus().equals(s.getStatus())));
            if (isExist) {
                continue;
            }
            list.add(entity);

        }

        System.out.println(JSONObject.toJSONString(list));



    }


    @Test
    public void test51 () throws Exception {
        String test = "1267___NSL";
        System.out.println(test.split("___")[0]);
        System.out.println(test.split("___")[1]);



    }

    @Test
    public void test52 () throws Exception {
        List<TestEntity> entities = new ArrayList<>();
        TestEntity entity = new TestEntity();
        entity.setStatus("A");
        entity.setCode("NSL");
        entities.add(entity);
        Map<String, List<TestEntity>> remainByPL = entities.stream().collect(Collectors.groupingBy(v -> v.getStatus() + "___" + v.getCode()));
        remainByPL.forEach((key, v)->{
            System.out.println(key.split("___")[0]);
            System.out.println(key.split("___")[1]);
        });
        System.out.println("123");



    }




    @Test(timeout = 10)
    public void test53 () throws Exception {
        Map<String, String> tenantMap = new HashMap<>();
        tenantMap.put("123", "123");
        tenantMap.put("31a", "21ad");
        tenantMap.put("as", "34df");
        // 组装部门信息
        JSONArray jsonArray = new JSONArray();
        tenantMap.forEach((tenantCode, tenantName) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tenantCode", tenantCode);
            jsonObject.put("tenantName", tenantName);
            jsonArray.add(jsonObject);
        });
        System.out.println(jsonArray.toJSONString());

    }

    @Test
    public void test54 () throws Exception {
        List<String> testList = new ArrayList<>();
        testList.add("1");
        testList.add("2");
        testList.add("3");
        testList.add("4");
        testList = testList.stream().filter(s->s.equals("2")).collect(Collectors.toList());
        System.out.println(12);
    }

//    @BeforeClass
    public static void beforeClass () {
        System.out.println("before class");
    }

//    @AfterClass
    public static void afterClass () {
        System.out.println("after class");
    }

//    @Before
    public void before () {
        System.out.println("before");
    }

//    @After
    public void after () {
        System.out.println("after");
    }

//    @Ignore
    @Test
    public void test55 () throws Exception {
//        String a = null;
//        a.equals("a");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 301; i++) {
            list.add("1");
        }
        int maxNum = 300;
        int count = (int) (Math.ceil(list.size() / ((double) maxNum)));
        for (int i = 0; i < count; i++) {
            // 获取每次需要执行的料
            int endIndex = (i + 1) * maxNum;
            if(i * maxNum > list.size()){
                break;
            }
            List<String> exeCodes = list.subList(i * maxNum, endIndex > list.size() ? list.size() : endIndex);
            System.out.println(12);
        }
        System.out.println(12);
    }

    @Test
    public void test56 () throws Exception {
        List<String> list = new ArrayList<>();
        String test = "null";
        if (notEmpty(test) && notEmpty(test.trim())) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }
    }

    private static Object o;

    @Test
    public void test57 () throws Exception {


        boolean needRetry = true;
        int retryNum = 1;
        String errMsg = null;
        String test = null;
        while (needRetry) {
            if (retryNum > 3) {
//                    needRetry = false;
                System.out.println("超时:" + retryNum);
                throw new Exception("123");
            }
            System.out.println("执行:" + retryNum);
            try {
//                boolean equals = test.equals("123");
                System.out.println("OK");
                needRetry = false;
            } catch (Exception e) {
                ++retryNum;
                errMsg = e.getMessage();
            }
        }
    }

    @Test
    public void test58 () throws Exception {
        String test = "123";
        String a = null;
        String b = null;

        System.out.println(StringUtils.pathEquals(a, b));

    }


    @Test
    public void test59 () throws Exception {
//        ReceiptVo vo = new ReceiptVo();
//        vo.setType("TEST2");
        ReceiptVo vo = new ReceiptVo();
        vo.setCode("TEST2");
        ReceiptHandleChain chain = new ReceiptHandleChain();
        chain.handleReceiptChain(vo);
        logger.info("123");

    }


    @Test
    public void test60 () throws Exception {
        ReceiptVo vo = new ReceiptVo();
        vo.setType("TEST1");

        DefaultHandler defaultHandler = new DefaultHandler();
        defaultHandler.handle(vo);


        logger.info("123");

    }


    @Test
    public void test61 () throws Exception {
        List<Object> res = new ArrayList<>();
        List<?> list = new ArrayList<>();
        list = null;
        res.addAll(list);

    }


    @Test
    public void test62 () {
        String str = "1";
        String s = checkblankStr(str);
        System.out.println(s);
    }

    @Test
    public void test63 () {
        TestEntity entity = new TestEntity();
        entity.setCode("");
        entity.setStatus("12");
        boolean status = validateProperty2(entity, Arrays.asList("status"), Arrays.asList("code"));
        System.out.println(12);
    }

    @Test
    public void test64 () {
        String timeRegex2 = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

        String timeRegex = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$";

        String test2 = "^(?:(?:1[6-9]|[2-9][0-9])[0-9]{2} ([-/.]?) (?:(?:0?[1-9]|1[0-2]) 1 (?:0?[1-9]|1[0-9]|2[0-8]) |(?:0?[13-9]|1[0-2]) 1 (?:29|30) |(?:0?[13578]|1[02]) 1 (?:31) )|(?:(?:1[6-9]|[2-9][0-9])(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00) ([-/.]?) 0?2 2 (?:29) )$";

        String da = "2000-03-31";

        String timeRegex3 = "^\\d{4}(\\/)\\d{1,2}\\1\\d{1,2}$";

        System.out.println(Pattern.matches(timeRegex3,da));
    }

    @Test
    public void test65 () {

        List<String> list = new ArrayList<>();

        Function<String, String> func1 = s->s.concat("apply");
        Function<String, String> func2 = s->s.concat("compose");
        Function<String, String> func3 = s->s.concat("andThen");

//        System.out.println(func1.apply("tzl"));
//        System.out.println(func1.compose(func2).apply("tzl"));
//        System.out.println(func1.andThen(func3).apply("tzl"));

//        int compute = compute("5", entityVo->entityVo.intValue());
        int compute = compute2(entityVo->entityVo.getNum());

        System.out.println(compute);
    }



    public static int compute2(Function<TestEntity, Long> func) {


        return 0;
    }

    public static int compute(String s, Function<Integer, Integer> func) {


        return func.apply(Integer.parseInt(s));
    }


    @Test
    public void test66 () {
        Integer a = 128;
        Integer b = 128;

        System.out.println(a.equals(b));

    }

    @Test
    public void test67 () {
        List<String> strings = JSON.parseArray("[100,101,102]", String.class);

        System.out.println(1);

    }

    @Test
    public void test68 () {

        ClassLayout classLayout = ClassLayout.parseClass(Object.class);
        System.out.println(classLayout.toPrintable());

    }

    @Test
    public void test69 () {

        int a = 1;
        change(a);
        System.out.println(a);
    }

    @Test
    public void test70 () {

        TestEntity originSpuPo = null;
        if (Utils.isEmpty(originSpuPo)) {
            System.out.println(1);
            originSpuPo = new TestEntity();
        }
        if (Utils.notEmpty(originSpuPo)) {
            System.out.println(2);
        }
    }

    @Test
    public void test71 () {
        StringBuilder guidePrice = new StringBuilder();
        guidePrice.append("20-45:").append("132313");

        String substring = guidePrice.substring(0, guidePrice.toString().length()-1);
        System.out.println(substring);
    }


    @Test
    public void test72 () {

        String divide = new BigDecimal("0.0123132131232132133231").multiply(new BigDecimal(1000)).setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        BigDecimal bigDecimal = new BigDecimal(divide);

        System.out.println(bigDecimal);
//        System.out.println(Math.ceil(200/4));

    }



    private void change (int a) {
        a = a + 1;
    }

    public static boolean validateProperty2 (Object validateObj, List<String> nullProperties, List<String> blankProperties) {
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(validateObj.getClass());
        Boolean flg = false;
        for (PropertyDescriptor targetPd : targetPds) {
            Method readMethod = targetPd.getReadMethod();
            // null检查
            if (readMethod != null) {
                try {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(validateObj);
                    // null判断
                    if (nullProperties != null && nullProperties.contains(targetPd.getName())) {
                        if (Utils.notEmpty(value)) {
                            return true;
                        }
                    }
                    // ""判断
                    if (blankProperties != null && blankProperties.contains(targetPd.getName())) {
                        if (value != null) {
                            return true;
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
        return flg;
    }


    public static List<String> validateProperty(Object validateObj, String... ignoreProperties) {
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(validateObj.getClass());
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        List<String> errList = new ArrayList<>();
        for (PropertyDescriptor targetPd : targetPds) {
            Method readMethod = targetPd.getReadMethod();
            if (readMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                try {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(validateObj);
                    if (value instanceof String) {
                        if (StringUtils.isEmpty((String) value)) {
                            errList.add(validateObj.getClass().getSimpleName()+ "中的" + targetPd.getName() + "不能为空");
                            continue;
                        }
                    }
                    if (value instanceof Float || value instanceof Integer) {
                        if (StringUtils.isEmpty(value.toString())) {
                            errList.add(validateObj.getClass().getSimpleName()+ "中的" + targetPd.getName() + "不能为空");
                            continue;
                        }
                    }
                    if (value == null) {
                        errList.add(validateObj.getClass().getSimpleName() + "中的" + targetPd.getName() + "不能为空");
                    }
                } catch (Throwable ex) {
                    throw new FatalBeanException(
                            "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                }
            }
        }
        return errList;
    }


    private String checkblankStr (String content) {
        if (content == null) {
            return null;
        }
        if (content == "") {
            return " ";
        }
        return content;
    }







    private List<DemoData> data() {
        List<DemoData> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串");
        List<String> head1 = new ArrayList<String>();
        head1.add("数字");
        List<String> head2 = new ArrayList<String>();
        head2.add("日期");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串哈哈啊哈啊啊哈啊哈哈哈哈啊哈" + i);
            data.add(new Date());
            data.add(0.56312124121231231);
            list.add(data);
        }
        return list;
    }

    @Data
    public class DemoData {
        private String string;
        private Date date;
        private Double doubleData;
    }












    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean notEmpty(Object arg) {
        return !isEmpty(arg);
    }
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }
















    @RequestMapping("/test")
    @ResponseBody
    public Integer itTest() {
        int sele = 0;
        int sele2 = 0;
        try {
            long s1 = System.currentTimeMillis();
            sele = multiTestService.ms();
            long e1 = System.currentTimeMillis();
            logger.info("sele {}", sele);
            logger.info("52库查询时间:{}", e1-s1);
            long s2 = System.currentTimeMillis();
            sele2 = multiTestService.ms2();
            long e2 = System.currentTimeMillis();
            logger.info("sele2 {}", sele2);
            logger.info("70库查询时间:{}", e2-s2);
        } catch (Exception e) {
            logger.error("数据库查询异常:{}", e);
            return sele;
        }
        return sele;
    }
}
