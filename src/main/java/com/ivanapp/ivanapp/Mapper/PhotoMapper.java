package com.ivanapp.ivanapp.Mapper;

import com.ivanapp.ivanapp.Pojo.TMerchant;
import com.ivanapp.ivanapp.Pojo.TPhoto;
import com.ivanapp.ivanapp.Pojo.TShow;
import com.ivanapp.ivanapp.Pojo.TSpecification;
import com.ivanapp.ivanapp.common.ResponseMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/3 14:58
 * 小程序页面
 * 套餐信息操作接口
 */
@Component
@Mapper
public interface PhotoMapper {
    /*获取商家信息 一家商家，因此只有一条记录*/
    @Select("select * from t_merchant")
    TMerchant getMerchantInfo();

    /*获取所有套餐的基础信息，获取全部套餐信息，也不多*/
    @Select("select * from t_photo")
    List<TPhoto> getPhotoLists();

    /*联表查inner(会产生笛卡尔积 多条重复记录) 查看单个套餐信息的时候 服务详情bean*/
    @Select("SELECT * FROM t_photo p INNER JOIN t_servicepar ser ON p.pid=ser.pId WHERE p.pid=#{pId}")
    TPhoto getPhotoAllInfoByPid(int pId);
    /*查询套餐对应的show图*/
    @Select("SELECT * FROM t_show WHERE pId=#{pId}")
    List<TShow> getShowListByPid(int pId);

    /*根据pid查询对应规格信息*/
    @Select("SELECT * FROM t_specification WHERE pid=#{pId}")
    List<TSpecification> getSpecificationInfoByPid(int pId);

    /**
     * 新增订单
     * @param pId 套餐id
     * @param openId 用户openId
     * @param speName 规格名
     * @param makeDate 预约时间
     * @param photoState 摄影状态 默认0 未拍摄
     * @param postState 取(寄)状态 用户选择 默认自取
     * @param tfetch 取片状态 默认0 未取
     * @param makeName 预约人名称
     * @param makePhoto 预约人联系方式
     * @param makeSex 预约人性别
     * @param payPrice 支付金额
     * @param sendSite 邮寄地址 自取默认为null
     * @param evaluate 订单评价 默认null 选填
     * @return
     */
    @Insert("insert into t_order(pId,openId,speName,makeDate,photoState,postState,tfetch,makeName,makePhone,makeSex,payPrice,sendSite,evaluate) " +
            "values (#{pId},#{openId},#{speName},#{makeDate},#{photoState},#{postState},#{tfetch},#{makeName},#{makePhoto},#{makeSex},#{payPrice}," +
            "#{sendSite},#{evaluate})")
    int addOrder(int pId, String openId, String speName, String makeDate, int photoState, int postState, int tfetch, String makeName,
                             String makePhoto, String makeSex, double payPrice, String sendSite, String evaluate);

    /**
     * 修改封面地址
     */
    @Update("update t_photo set coverImg=#{coverImg} where pid=#{pId}")
    int upCoverImg(int pId,String coverImg);

}
