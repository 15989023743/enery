/**
 * Created by lemon on 14-6-23.
 */


sliderMaster=function(target,stay,toBigConf){
    this.toBigConf=toBigConf;
    this.dataMap={};
    this.obj=jQuery(target);
    this.obj.append(this.obj.html());
    var temp=jQuery(target+" > div");
    this.innerDiv=jQuery(temp[0]);
    this.innerDiv.css({position:"absolute","left":"0px",height:"30px","vertical-align": "middle"});
    this.innerDiv2=jQuery(temp[1]);
    if(this.innerDiv.width()>this.obj.width()){
        this.innerDiv2.css({position:"absolute","left":this.innerDiv.width()+"px",height:"30px","vertical-align": "middle"});
    }else{
        this.innerDiv2.css({position:"absolute","left":this.obj.width()+"px",height:"30px","vertical-align": "middle"});
    }
    this.dataMap["m1"]=true;
    this.dataMap["m2"]=false;
    this.innerDiv.data("y","m2");
    this.innerDiv2.data("y","m1");
    this.innerDiv.data("m","m1");
    this.innerDiv2.data("m","m2");
    this.mainLeft=this.obj.offset().left;
    this.stay=stay;
    this.start();
    var TH=this;
    this.obj.mouseover(function(){
        TH.stop();
    });
    this.obj.mouseout(function(){
        TH.start();
    });
    this.mleft=null;
    this.mright=null;
    if(this.toBigInit()){
        this.mleft=parseInt(this.magnifying.css("left").replace("px",""));
        this.mright=parseInt(this.magnifying.css("left").replace("px",""))+this.magnifying.width();
        this.fontMap={};
        this.tomax=10;
        var len=this.mright-this.mleft;
        var step=len/this.tomax;
        var b=1;
        var font=this.fontSize;
        for(var i=0;i<len;i++){
            if(i==parseInt(len/2)){
                b=-1;
            }
            if(i%step==0){
                font=font+b;
            }
            this.fontMap[this.mleft+i]=font;
        }
        this.splitWord(this.innerDiv);
        this.splitWord(this.innerDiv2);
    }

    var top=this.obj.height()/2-this.innerDiv.height()/2;
    this.innerDiv.css({"top":top+"px",height:"30px","vertical-align": "middle"});
    this.innerDiv2.css({"top":top+"px",height:"30px","vertical-align": "middle"});
}


sliderMaster.prototype={
    start:function(){
        var TH=this;
        var moveFun=function(){
            TH.moveFun(TH,TH.innerDiv);
            TH.moveFun(TH,TH.innerDiv2);
        };
        this.inv=setInterval(moveFun,TH.stay);
    },
    stop:function(){
        clearInterval(this.inv);
    },
    moveFun:function(context,ins){
        if(!context.dataMap[ins.data("m")]){
            return;
        }
        if(parseInt(ins.css("left").replace("px",""))==0){
            context.dataMap[ins.data("y")]=true;
        }

        if(parseInt(ins.css("left").replace("px",""))*-1>=ins.width()){
            ins.css("left",context.obj.width()+"px");
            context.dataMap[ins.data("m")]=false;
        }else{
            var left=parseInt(ins.css("left").replace("px",""));
            ins.css("left",(left-1)+"px");
        }
        if(context.mleft&&context.mright){
            var insLeft=parseInt(ins.css("left").replace("px",""));
            var insRight=insLeft+ins.width();
            if((insLeft<context.mleft&&insRight>context.mleft)||(insLeft<context.mright&&insRight>context.mright)){
                context.toMagnifying(context,ins);
            }
        }
    },
    toBigInit:function(){
        this.miniWidth=100;
        this.miniHeight=this.obj.height();
        this.miniLeft=this.obj.width()/2;
        this.miniTop=0;
        if(this.toBigConf){
            this.magnifying=jQuery("<div style='border: 1px red solid'></div>");
            this.obj.append(this.magnifying);
            this.magnifying.css({"position":"absolute","z-index":"99999"});
            if(this.toBigConf.width){
                this.magnifying.css("width",this.toBigConf.width+"px");
            }else{
                this.magnifying.css("width",this.miniWidth+"px");
            }
            if(this.toBigConf.height){
                this.magnifying.css("height",this.toBigConf.height+"px");
            }else{
                this.magnifying.css("height",this.miniHeight+"px");
            }
            if(this.toBigConf.top){
                this.magnifying.css("top",this.toBigConf.top+"px");
            }else{
                this.magnifying.css("top",this.miniTop+"px");
            }
            if(this.toBigConf.left){
                this.magnifying.css("left",this.toBigConf.left+"px");
            }else{
                this.magnifying.css("left",this.miniLeft+"px");
            }
            if(this.toBigConf.fontSize){
                this.fontSize=this.toBigConf.fontSize;
            }else{
                this.fontSize=12;
            }
            return true;
        }
        return false;
    },
    splitWord:function(ins){
        var words=ins.html().replace(" ","").split("");
        ins.html("");
        for(var i in words){
            ins.append("<span style='width: 20px;height: 30px;padding-top:5px;vertical-align: middle;font-size: "+this.fontSize+"px;'>"+words[i]+"</span>");
        }
    },
    toMagnifying:function(context,ins){
        var mainLeft=parseInt(ins.css("left").replace("px",""));
        ins.find("span").each(function(){
            var temp=jQuery(this);
            var sett=parseInt(temp.width()/2);
            var fontset=mainLeft+temp.position().left+sett;
            var fontsize=context.fontMap[fontset];
            if(fontsize){
                if(fontsize+"px"==temp.css("font-size")){
                    return;
                }
                //var resize=parseInt(temp.css("font-size").replace("px",""));
                //var repd=parseInt(temp.css("padding-top").replace("px",""));
                temp.css({"font-size":fontsize+"px"});
            }else{
                temp.css({"font-size":context.fontSize+"px"});
            }
        });
    }
};
