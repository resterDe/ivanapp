package com.ivanapp.ivanapp.Controller;

import com.ivanapp.ivanapp.Mapper.PhotoMapper;
import com.ivanapp.ivanapp.Service.MessageService;
import com.ivanapp.ivanapp.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/3 16:59
 * 小程序页面数据管理
 * 1、初始化数据->套餐基础信息
 * 2、单个套餐详细信息
 * 3、点击预约后套餐对应规格信息
 * 4、添加新订单
 */
@Slf4j
@RestController
@CrossOrigin // 说是解决跨域问题的（因为我页面不是在这里写的，因此会存在跨域问题）
@RequestMapping("message-api")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 获取商家信息
     *
     * @return
     */
    @GetMapping("getMerchantInfo")
    public ResponseMessage getMerchantInfo() {
        ResponseMessage responseMessage = messageService.getMerchantInfo();
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 获取套餐基础信息
     *
     * @return
     */
    @GetMapping("getPhotoLists")
    public ResponseMessage getPhotoLists() {
        ResponseMessage responseMessage = messageService.getPhotoLists();
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 获取套餐详细信息
     * 套餐信息、展示图、服务详情
     *
     * @param pId
     * @return
     */
    @GetMapping("getPhotoAllInfo")
    public ResponseMessage getPhotoAllInfo(int pId) {
        ResponseMessage responseMessage = messageService.getPhotoAllInfoByPid(pId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 点击预约后
     * 获取套餐对应规格信息
     *
     * @param pId
     * @return
     */
    @GetMapping("getSpeInfo")
    public ResponseMessage getSpeInfo(int pId) {
        ResponseMessage responseMessage = messageService.getSpecificationInfoByPid(pId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 新增订单
     *
     * @param pId       套餐id
     * @param openId    用户openId
     * @param speName   规格名
     * @param makeDate  预约时间
     *                  ***photoState 摄影状态 默认0 未拍摄
     * @param postState 取(寄)状态 用户选择 默认自取0 快递1
     *                  ***fetch 取片状态 默认0 未取 1已取
     * @param makeName  预约人名称
     * @param makePhoto 预约人联系方式
     * @param makeSex   预约人性别
     * @param payPrice  支付金额 负数为支付异常
     * @param sendSite  邮寄地址 自取默认设为null
     *                  ***evaluate 订单评价 默认null 选填
     * @return
     */
    @PostMapping("addOrder")
    public ResponseMessage addOrder(int pId, String openId, String speName, String makeDate, int postState, String makeName,
                                    String makePhoto, String makeSex, double payPrice, String sendSite) {
        ResponseMessage responseMessage = messageService.addOrder(pId, openId, speName, makeDate,
                0, postState, 0, makeName, makePhoto, makeSex, payPrice, sendSite, null);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 获取用户订单
     * @param openId
     * @return
     */
    @GetMapping("getOrderList")
    public ResponseMessage getOrderList(String openId){
        ResponseMessage responseMessage = messageService.getOrderByOpenId(openId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 取消订单
     * photoState 0 未拍摄 1 已拍摄 2 已取消
     * @param oId
     * @return
     */
    @GetMapping("cancelOrder")
    public ResponseMessage cancelOrder(int oId){
        ResponseMessage responseMessage = messageService.cancelOrder(2, oId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 用户评价订单
     * @param oId
     * @param evaluate
     * @return
     */
    @GetMapping("userEvaluate")
    public ResponseMessage userEvaluate(int oId,String evaluate){
        ResponseMessage responseMessage = messageService.userEvaluate(oId, evaluate);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 删除规格
     * @param speId
     * @return
     */
    @PostMapping("delSpe")
    public ResponseMessage delSpe(int speId){
        ResponseMessage responseMessage = messageService.delSpeById(speId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 添加规格
     * @param pId
     * @param speName
     * @param price
     * @return
     */
    @PostMapping("addSpe")
    public ResponseMessage addSpe(int pId, String speName, double price){
        ResponseMessage responseMessage = messageService.addSpe(pId, speName, price);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage(responseMessage.getMsg());
    }

    /**
     * 修改封面图
     * 直接置换
     * @param file
     * @return
     */
    @PostMapping("upCoverImg")
    public ResponseMessage upCoverImg(@RequestParam("file") MultipartFile file, int pId, HttpServletRequest request) throws IOException {
        //获取的文件名
        int fileName;
        //获取的文件拓展名
        String fileHzm;
        //定义文件上传的路径
        String filePath;
        //设置数据库保存路径
        String sowingImg;
        //新文件名 以pId命名
        fileName = pId;
        //获取文件拓展名
        fileHzm = FilenameUtils.getExtension (file.getOriginalFilename ());
        filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static/images/cover/";
        System.out.println("获取的路径"+filePath);
        //创建文件对象
        File fileUrl = new File (filePath);
        System.out.println("文件路径："+fileUrl);
        //创建文件路径
        fileUrl.mkdirs ();
        //以绝对路径保存重命名后的图片
        file.transferTo (new File (filePath + "\\" + fileName + "." + fileHzm));
        //设置数据库保存路径
        sowingImg = "/images/cover/" + fileName + "." + fileHzm;
        System.out.println(sowingImg);
        // 将路径入库
        return messageService.upCoverImg(pId,sowingImg);
    }

    /**
     * 置换展示图
     * 直接置换
     * @param file
     * @return
     */
    @PostMapping("saveShowImg")
    public ResponseMessage saveShowImg(@RequestParam("file") MultipartFile file, int pId, HttpServletRequest request) throws IOException {
        //新文件名 以pId命名
        String fileName = UUID.randomUUID ().toString ().replaceAll ("-", "");
        //获取文件拓展名
        String fileHzm = FilenameUtils.getExtension (file.getOriginalFilename ());
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static/images/lists/" + pId + "/";
        //创建文件对象
        File fileUrl = new File (filePath);
        //创建文件路径
        fileUrl.mkdirs ();
        //以绝对路径保存重命名后的图片
        file.transferTo (new File (filePath + "\\" + fileName + "." + fileHzm));
        //设置数据库保存路径
        String sowingImg = "/images/lists/" + pId + "/" + fileName + "." + fileHzm;
        System.out.println(sowingImg);
        // 判断是否已经成功清空原有图片
        ResponseMessage responseMessage = messageService.delShowList(pId);
        System.out.println(responseMessage.isSuccess());
        if (responseMessage.isSuccess())
            // 将路径入库
            return messageService.addShowList(pId,sowingImg);
        else
            return ResponseMessage.createByErrorMessage("清空错误");
    }

    /**
     * 修改套餐基础信息
     * @param modelling
     * @param shoot
     * @param anaphase
     * @param plot
     * @param afterSale
     * @param explain
     * @param pId
     * @return
     */
    @PostMapping(value = "upSerInfo",produces = {"application/json; charset=UTF-8"})
    public ResponseMessage upSerInfo(String modelling, String shoot, String anaphase,
                                     String plot, String afterSale, String explain, int pId){
        System.out.println(explain);
        // 执行修改 马马虎虎写了
        return messageService.upSerInfo(modelling, shoot, anaphase, plot, afterSale, explain, pId);
    }
}
