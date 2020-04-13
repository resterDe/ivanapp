package com.ivanapp.ivanapp.Mapper;

import com.ivanapp.ivanapp.Pojo.TOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/2 16:31
 * 订单t_order操作接口
 */
@Component
@Mapper
public interface OrderMapper {
    /**
     * 根据openId 查询用户订单信息
     * 联表查询 订单对应的信息和套餐信息
     */
    @Select("SELECT * FROM t_order o INNER JOIN t_photo p ON o.pId=p.pId WHERE openId=#{openId}")
    List<TOrder> getOrderByOpenId(String openId);

    /**
     * 用户取消订单，根据订单id，将摄影状态photoState设置为3已取消
     */
    @Update("update t_order set photoState=#{photoState} where oId=#{oId}")
    int cancelOrder(int photoState,int oId);

    /**
     * 用户提交评价
     */
    @Update("update t_order set evaluate=#{evaluate} where oId=#{oId}")
    int userEvaluate(int oId,String evaluate);

    /**
     * 查询全部预约信息
     * layui默认传递两个参数
     * 联表查询套餐名称
     * 全部
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getOrderAllInfo(int pages, int limit);

    /**
     * 根据用户联系方式查询用户
     * @param pages
     * @param limit
     * @param makePhone 预约联系方式
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE o.makePhone=#{makePhone} ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getOrderInfoByPhone(int pages, int limit,String makePhone);

    /**
     * 查询未拍摄预约信息
     * @param photoState = 0
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE o.photoState=#{photoState} ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getOrderNoPhoto(int pages, int limit,int photoState);

    /**
     * 查询已取片预约信息
     * @param photoState = 1
     * @param  tfetch = 1
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE o.photoState=#{photoState} AND o.tfetch=#{photoState} ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getOrderIsFetch(int pages, int limit,int photoState,int tfetch);

    /**
     * 查询未取片预约信息
     * @param photoState = 1
     * @param  tfetch = 0
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE o.photoState=#{photoState} OR o.tfetch=#{photoState} ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getOrderNoFetch(int pages, int limit,int photoState,int tfetch);

    /**
     * 查询已取消预约信息
     * @param photoState = 2
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE o.photoState=#{photoState} ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getNoOrderInfo(int pages, int limit, int photoState);

    /**
     * 根据oId查询单个预约的信息
     * 虽然没什么用，不过还是习惯性写了
     * @param oId
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE o.oId=#{oId}")
    TOrder getOrderOneInfo(int oId);

    /**
     * 查询有评价的预约信息
     *  evaluate = null or ''
     * @return
     */
    @Select("SELECT o.*,p.photoName FROM t_order o INNER JOIN t_photo p ON o.pId=p.pid WHERE (ISNULL(o.evaluate)=0 AND LENGTH(trim(o.evaluate))>0) ORDER BY o.oId DESC LIMIT #{pages},#{limit}")
    List<TOrder> getOrderEvaluate(int pages, int limit);

    /**
     * 编辑摄影状态 将其设置为已拍摄 photoState = 1
     */
    @Update("update t_order set photoState=#{photoState} where oId=#{oId}")
    int upPhotoState(int photoState,int oId);

    /**
     * 编辑摄影状态 将其设置为已取片 tfetch = 1
     */
    @Update("update t_order set tfetch=#{tfetch} where oId=#{oId}")
    int upTfetchState(int tfetch,int oId);

    /**
     * 删除对应的预约信息
     * @param oId
     * @return
     */
    @Delete("delete from t_order where oId=#{oId}")
    int delOrderById(int oId);
}
