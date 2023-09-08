package com.example.d3.desingpatterns;

import java.util.Arrays;

/**
 * @author wangchao
 * @Description:职责链
 * @date 2023/9/4 16:17
 * @vVersion 1.0
 */
public class ResponsibilityChain {
    public static void main(String[] args) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(1,1999,100);
        DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
        CollegeApprover collegeApprover = new CollegeApprover("李院长");
        ViceSchoolMasterApprover viceSchoolMasterApprover = new ViceSchoolMasterApprover("王副校");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("佟校长");

        //需要将各个审批级别的下一个设置好 (处理人构成环形: )
        viceSchoolMasterApprover.setApprover(schoolMasterApprover);
        schoolMasterApprover.setApprover(departmentApprover);
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(viceSchoolMasterApprover);




        departmentApprover.processRequest(purchaseRequest);



    }

}
abstract class Approver {

    Approver approver;  //下一个处理者
    String name; // 名字
    private float[] myPriceRange;
    public Approver(String name) {
        // TODO Auto-generated constructor stub
        this.name = name;
    }

    //下一个处理者
    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    public  void processRequest(PurchaseRequest purchaseRequest){
        System.out.println("当前名称=="+this.name+"==审批金额权限："+ Arrays.toString(this.myPriceRange));
        if(purchaseRequest.getPrice() >=this.myPriceRange[0]  && purchaseRequest.getPrice() <= this.myPriceRange[1]) {
            System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理,审批金额："+ Arrays.toString(this.myPriceRange));
        }else {
            System.out.println(this.approver.getClass().getName());
            approver.processRequest(purchaseRequest);
        }
    };

    public void setMyPriceRange(float start,float end) {
        this.myPriceRange = new float[]{ start, end};
    }

}
class PurchaseRequest {

    private int type = 0; //请求类型
    private float price = 0.0f; //请求金额
    private int id = 0;
    //构造器
    public PurchaseRequest(int type, float price, int id) {
        this.type = type;
        this.price = price;
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public float getPrice() {
        return price;
    }
    public int getId() {
        return id;
    }

}
class CollegeApprover extends Approver {

    public CollegeApprover(String name) {
        // TODO Auto-generated constructor stub
        super(name);
        super.setMyPriceRange(8000,200000);
    }
}
class DepartmentApprover extends Approver {


    public DepartmentApprover(String name) {
        // TODO Auto-generated constructor stub
        super(name);
        super.setMyPriceRange(6000,7999);
    }

}
class SchoolMasterApprover extends Approver {

    public SchoolMasterApprover(String name) {
        // TODO Auto-generated constructor stub
        super(name);
        super.setMyPriceRange(3000,5999);
    }

}
class ViceSchoolMasterApprover extends Approver {

    public ViceSchoolMasterApprover(String name) {
        // TODO Auto-generated constructor stub
        super(name);
        super.setMyPriceRange(0,2999);
    }

}
