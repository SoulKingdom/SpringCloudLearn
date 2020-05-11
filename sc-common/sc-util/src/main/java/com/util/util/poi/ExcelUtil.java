package com.util.util.poi;


import com.util.util.poi.annotation.ChineseName;
import com.util.util.poi.annotation.ColumnID;
import com.util.util.poi.annotation.ExcelFileName;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @dept 上海软件研发中心
 * @function Excel 导入 导出工具类
 * @author HaoXin.Liu
 * @date 2020/3/25 17:19
 **/
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static final String EXCEL_EXTENDS_NAME_XLS = ".xls";

    public static final String EXCEL_EXTENDS_NAME_XLSX = ".xlsx";

    public static String fileName = "templet";

    public static final int MAX_NUM = 65535;


    /**
     * 创建Excel模板(单个sheet对象进行模板下载)
     *
     * @dept 上海软件研发中心
     * @param cls 数据集 （面向对象思想 DAO 数据访问对象）需要创建的sheet
     * @return 返回Excel中sheet的标题名称
     * @author HaoXin.Liu
     * @date 2019/4/4 10:16
     **/
    public static <T> String createExcelSheetTemplet(HttpServletRequest request, HttpServletResponse response, Class<?> cls) {
        //1.1创建 excel 工作薄
        Workbook wb = new HSSFWorkbook();
        //获取ExcelSheet名称通过注解
        String tittle = getExcelFileNameByAnno(cls);
        //1.2 创建 sheet
        Sheet sheet = wb.createSheet(tittle);
        //1.3 创建表格数据
        createExcelTemplet(sheet, tittle, cls);
        try {
            //设置下载状态
            downloadSet(request, response, wb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tittle;
    }

    /**
     * 获取反射类的文件类注解名称
     *
     * @dept 上海软件研发中心
     * @param cls 反射类
     * @return
     * @author HaoXin.Liu
     * @date 2019/11/4 15:45
     **/
    private static String getExcelFileNameByAnno(Class<?> cls) {
        ExcelFileName fileNameAnn = cls.getAnnotation(ExcelFileName.class);
        //4. 判断 检查 @ExcelFileName
        if (fileNameAnn == null) {
            throw new RuntimeException("请检查【" + cls.getName() + "】有没有@ExcelFileName注解");
        }
        //5. 获得 该注解 的 value 属性
        return fileNameAnn.value();
    }

    /**
     * 创建Excel模板(多个sheet对象进行模板下载)
     *
     * @dept 上海软件研发中心
     * @param clses 数据集 （面向对象思想 DAO 数据访问对象）需要创建的多个sheet
     * @return 返回Excel中sheet的标题名称
     * @author HaoXin.Liu
     * @date 2019/4/4 10:16
     **/
    public static <T> String createExcelSheetsTemplet(HttpServletRequest request, HttpServletResponse response, List<Class> clses) {
        //1.1创建 excel 工作薄
        Workbook wb = new HSSFWorkbook();
        for (Class<?> cls : clses) {
            //注解名称
            String tittle = getExcelFileNameByAnno(cls);
            //1.2 创建 sheet
            Sheet sheet = wb.createSheet(tittle);
            //1.3 创建表格数据
            createExcelTemplet(sheet, tittle, cls);
        }
        try {
            //设置下载状态
            downloadSet(request, response, wb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName + EXCEL_EXTENDS_NAME_XLS;
    }

    /**
     * 前台下载信息头
     * @param response 响应信息
     * @param wb excel表格信息
     * @throws IOException
     */
    private static void downloadSet(HttpServletRequest request, HttpServletResponse response, Workbook wb) throws IOException {
        downloadSet(request, response, wb, null);
    }

    /**
     *
     * 前台下载信息头
     * @param response 响应信息
     * @param wb excel表格信息
     * @param fileName excel文件名称
     * @throws IOException
     */
    private static void downloadSet(HttpServletRequest request, HttpServletResponse response, Workbook wb, String fileName) throws IOException {
        //获取前台相应工作流
        OutputStream output = response.getOutputStream();
        //响应信息重置
        response.reset();
        //设置编码
        response.setCharacterEncoding("UTF-8");
        //设置响应头
        setHeader(request, response, fileName);
        //设置响应类型
        response.setContentType("application/msexcel");
        //设置数据头
        response.setDateHeader("Expires", 0);
        //响应信息刷新
        response.flushBuffer();
        //信息写入
        wb.write(output);
        //流关闭
        output.close();
    }

    /**
     * 设置响应头信息
     * @param response 响应头
     * @param fileCurrName excel自定义名称
     */
    private static void setHeader(HttpServletRequest request, HttpServletResponse response, String fileCurrName) {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        try {
            if (StringUtils.isBlank(fileCurrName)) {
                //默认下载名称
                response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } else {
                //不同浏览器解析方式不同
                if (userAgent != null && userAgent.indexOf("Firefox") >= 0
                        || userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0) {
                    fileCurrName = new String(fileCurrName.getBytes(), "ISO8859-1");
                } else {
                    // 其他浏览器
                    fileCurrName = URLEncoder.encode(fileCurrName, "UTF8");
                }
                //自定义下载名称
                response.setHeader("Content-disposition", "attachment; filename=" + fileCurrName);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("=======设置响应头信息出错=======");
            e.printStackTrace();
        }
    }

    /**
     * @dept 上海软件研发中心
     * @function 导出Excel数据
     * @param dataList 数据集 （面向对象思想 DAO 数据访问对象）
     * @param tittle   Excel标题名称
     * @return String 下载的文件名称
     * @author HaoXin.Liu
     * @date 2019/4/3 18:08
     **/
    public static String exportExcelTo(HttpServletRequest request, HttpServletResponse response, String tittle, Collection<?> dataList) {
        //计算导出excel数量
        int listSize = dataList.size();
        int excelNum = listSize % MAX_NUM == 0 ? listSize / MAX_NUM : listSize / MAX_NUM + 1;
        //创建Excel对象数量
        for (int i = 0; i < excelNum; i++) {
            //1.1创建 excel 工作薄
            Workbook wb = new HSSFWorkbook();
            //2.自定义的方法 获得新的 文件名
            String name = getName(dataList);
            //1.2 创建 sheet
            Sheet sheet = wb.createSheet(tittle);
            //1.3 创建表格数据
            createExcelData(sheet, tittle, dataList);
            try {
                //设置下载状态
                downloadSet(request, response, wb, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return name;
    }

    /**
     * 导出Excel表格 存入固定路径
     *
     * @param path     excel生成的路径
     * @param dataList 数据集 （面向对象思想 DAO 数据访问对象）
     * @return excel 表格 名称
     */
    public static String exportExcel(String path, String tittle, Collection<?> dataList) {
        //1.1创建 excel 工作薄
        Workbook wb = new HSSFWorkbook();
        //2.自定义的方法 获得新的 文件名
        String name = getName(dataList);
        //1.2 创建 sheet
        Sheet sheet = wb.createSheet(tittle);
        //1.3 创建表格数据
        createExcelData(sheet, tittle, dataList);
        try {
            //3. 通过 路径 ，文件名 创建 file 对象
            File file = new File(path, name);
            //4. 检查文件夹是否存在
            if (!file.getParentFile().exists()) {
                // 创建文件夹
                file.getParentFile().mkdirs();
            }
            OutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * @dept 上海软件研发中心
     * @function Excel文件导入
     * @param  file 上传的文件信息
     * @param  entityCls 实体类
     * @return Map 读取文件中的Excel表格数据存在Map对象中
     * @author HaoXin.Liu
     * @date 2019/4/4 11:23
     **/
    public static Map<String, Object> importExcelTo(MultipartFile file, Class<?> entityCls) {
        Map<String, Object> dataMap = null;
        try {
            InputStream is = file.getInputStream();
            Workbook wb = new HSSFWorkbook(is);
            //3. 获得 @ExcelFileName 对象
            String fileName = getExcelFileNameByAnno(entityCls);
            Sheet sheet = wb.getSheet(fileName);
            //Sheet sheet = wb.getSheetAt(0);
            dataMap = mappingExcel2Bean(sheet, entityCls);
            wb.close();
        } catch (Exception e) {
            logger.info("读取Excel代码");
            e.printStackTrace();
        }

        return dataMap;
    }

    /**
     * 固定路径Excel文件导入
     * @param path 文件路径
     * @param fileName 文件名称
     * @param entityCls 接收对象类型文件
     * @return map 映射对象信息
     */
    public static Map<String, Object> importExcel(String path, String fileName, Class<?> entityCls) {
        Map<String, Object> dataMap = null;
        File file = new File(path, fileName);
        if (!file.exists()) {
            throw new RuntimeException("指定目录【" + path + "】下,找不到【" + fileName + "】文件!!!");
        }
        try {
            InputStream is = new FileInputStream(file);
            Workbook wb = new HSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            dataMap = mappingExcel2Bean(sheet, entityCls);
            wb.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dataMap;
    }

    private static Map<String, Object> mappingExcel2Bean(Sheet sheet, Class<?> entityCls) {
        Map<String, Object> dataMap = null;
        try {
            //获取对象中所有属性的名称数组
            Field[] fieldAy = entityCls.getDeclaredFields();
            //1. ID 属性对象
            List<Field> fieldList = new ArrayList<>();
            dataMap = new HashMap<>();
            //单元格第一行 对应行的数据
            Row headRow = sheet.getRow(1);
            //第一行单元格中属性值
            for (int i = 0; i < headRow.getLastCellNum(); i++) {
                //获得对应i列的单元格
                Cell cell = headRow.getCell(i);
                //获得单元格中的值
                String value = cell.getStringCellValue();
                //对象属性集合
                for (Field field : fieldAy) {
                    //该属性的注解是否存在 并且 单元格名称
                    if (field.getAnnotation(ChineseName.class) != null && value.equals(field.getAnnotation(ChineseName.class).value())) {
                        //允许通过反射访问字段
                        field.setAccessible(true);
                        //将字段添加到属性对象中
                        fieldList.add(field);
                    }
                }
            }
            //获取最后一行数
            int rowCount = sheet.getLastRowNum();
            for (int i = 2; i <= rowCount; i++) {
                Object entity = entityCls.newInstance();
                String id = null;
                Row row = sheet.getRow(i);
                //循环匹配EXCEL中和属性匹配的个数
                for (int j = 0; j < fieldList.size(); j++) {
                    //获得对应属性的单元格值
                    Cell cell = row.getCell(j);
                    //读取数据前设置单元格类型
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    //通过反射未对应entity的属性赋值
                    fieldList.get(j).set(entity, value);
                    //判断属性对象对应注解是否存在
                    if (fieldList.get(j).getAnnotation(ColumnID.class) != null) {
                        id = value;
                    }
                }
                //数据填入map
                dataMap.put(id, entity);
            }
        } catch (Exception e) {
            logger.info("Excel数据反射存入Map失败");
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * Collection 集合中的数据 excel 数据
     * @param dataList
     */
    private static void createExcelData(Sheet sheet, String tittle, Collection<?> dataList) {
        try {
            Class<?> cls = getEntityClass(dataList);
            Field[] fieldAy = createExcelTemplet(sheet, tittle, cls);
            //2.数据
            Iterator<?> it = dataList.iterator();
            int rowIdx = 2;
            while (it.hasNext()) {
                Object data = it.next();
                Row row = sheet.createRow(rowIdx);
                for (int i = 0; i < fieldAy.length; i++) {
                    Field field = fieldAy[i];
                    field.setAccessible(true);
                    Object value = field.get(data);
                    if (value instanceof String) {
                        row.createCell(i).setCellValue((String) value);
                    }
                }
                rowIdx++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @dept 上海软件研发中心
     * @function TODO 创建Excel模板
     * @param
     * @return TODO
     * @author HaoXin.Liu
     * @date 2019/4/4 10:13
     **/
    private static Field[] createExcelTemplet(Sheet sheet, String tittle, Class<?> cls) {
        //获取对象属性数组
        Field[] fieldAy = cls.getDeclaredFields();
        //创建标题
        Row tittleRow = sheet.createRow(0);
        //创建标题名称
        tittleRow.createCell(0).setCellValue(tittle);
        //创建单元格
        //1.创建excel 表头
        Row headRow = sheet.createRow(1);
        for (int i = 0; i < fieldAy.length; i++) {
            Field field = fieldAy[i];
            String chinaName = field.getAnnotation(ChineseName.class).value();
            headRow.createCell(i).setCellValue(chinaName);
        }
        return fieldAy;
    }

    /**
     * 根据 数据 信息 创建 excel 文件名
     * @param dataList
     * @return
     */
    private static String getName(Collection<?> dataList) {
        Class<?> cls = getEntityClass(dataList);
        //3. 获得 @ExcelFileName 对象
        String fileName = getExcelFileNameByAnno(cls);
        //6. 创建时间戳  yyyy-MM-dd-HH-mm  或 当前时间到 1970.1.1 毫秒数  new Date().getTime();
        return fileName + new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date()) + EXCEL_EXTENDS_NAME_XLS;
    }

    private static Class<?> getEntityClass(Collection<?> dataList) {
        //1. 从集合中 获得一个 元素
        Iterator<?> it = dataList.iterator();
        Object obj = it.next();
        //2. 通过 obj 对象 获得 po 的Class对象
        Class<?> cls = obj.getClass();
        return cls;
    }
}
