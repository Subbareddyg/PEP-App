(function(){
if(typeof wptheme==="undefined"||!wptheme){
wptheme={};
}
i$.mash(wptheme,{mobileGoToSearch:function(id,_1,_2,_3,_4,_5,_6){
var _7=document.getElementById(_4);
var _8=document.getElementById("wpthemeSearchBoxInput");
if(i$.hasClass(_7,_1)){
wptheme.toggleMobileNav(id,_1,_2,_3,_4,_5,_6);
if(_8){
setTimeout(function(){
_8.focus();
},550);
}
}else{
if(_8){
_8.focus();
}
}
},mobileNavResizeBinding:null,mobileNavSideLastExpanded:[],mobileNavSideExpanded:[],mobileNavSideTogglingRoot:false,resizeMobileNavSide:function(e){
var _9=document.getElementsByTagName("header")[0];
var _a,id;
_a=document.getElementById("wpthemeNavRoot");
if(_a){
_a.style.top=_9.offsetHeight+"px";
_a.style.height=(window.innerHeight-_9.offsetHeight)+"px";
}
for(var i=0;i<wptheme.mobileNavSideExpanded.length;i++){
id=wptheme.mobileNavSideExpanded[i];
id=id.substr(0,id.length-4)+"Subnav";
_a=document.getElementById(id);
if(_a){
_a.style.top=_9.offsetHeight+"px";
_a.style.height=(window.innerHeight-_9.offsetHeight)+"px";
}
}
},animateMobileNavSidePanel:function(_b,_c,_d){
var _e=_b.offsetLeft;
if(ibmCfg.themeConfig.isRTL){
_e=parseInt(_b.style.right);
}
if(this.mobileNavSideTogglingRoot&&_c<_e){
_c=-_b.offsetWidth;
}
var _f=0;
var _10=Math.ceil(Math.abs(_c-_e)/5);
if(_e==_c){
_10=0;
}else{
if(_e>_c){
_10=-_10;
}
}
if(_10>0&&!this.mobileNavSideTogglingRoot){
if(ibmCfg.themeConfig.isRTL){
_b.style.clip="rect(0px,0px,"+_b.offsetHeight+"px,0px)";
}else{
_b.style.clip="rect(0px,"+_b.offsetWidth+"px,"+_b.offsetHeight+"px,"+_b.offsetWidth+"px)";
}
}
var _11=false;
var _12=setInterval(function(){
if(_11){
clearInterval(_12);
_b.style.clip="";
if(_d){
_d.call();
}
}else{
if(_10>0&&_e+_10>_c){
_10=_c-_e;
}
if(_10<0&&_e+_10<_c){
_10=-(_e-_c);
}
if(_10<0&&!wptheme.mobileNavSideTogglingRoot){
if(ibmCfg.themeConfig.isRTL){
_b.style.clip="rect(0px,"+(_b.offsetWidth-(_f-=_10))+"px,"+_b.offsetHeight+"px,0px)";
}else{
_b.style.clip="rect(0px,"+_b.offsetWidth+"px,"+_b.offsetHeight+"px,"+(_f-=_10)+"px)";
}
}
if(_10>0&&!wptheme.mobileNavSideTogglingRoot){
if(ibmCfg.themeConfig.isRTL){
_b.style.clip="rect(0px,"+(_f+=_10)+"px,"+_b.offsetHeight+"px,0px)";
}else{
_b.style.clip="rect(0px,"+_b.offsetWidth+"px,"+_b.offsetHeight+"px,"+(_b.offsetWidth-(_f+=_10))+"px)";
}
}
if(ibmCfg.themeConfig.isRTL){
_b.style.right=(_e+=_10)+"px";
}else{
_b.style.left=(_e+=_10)+"px";
}
if(_10>0&&_e>=_c||_10<0&&_e<=_c||_10==0){
_11=true;
}
}
},1);
},hideMobileNav:function(){
var _13=document.getElementById("wpthemeNavRootLink");
_13.style.display="none";
},toggleMobileNav:function(id,_14,_15,_16,_17,_18,_19){
var _1a=document.getElementById(id);
var _1b=document.getElementById("wpthemeNavRootLink");
var _1c=document.getElementById(id+"Link");
var _1d=document.getElementById(id+"Access");
var _1e=document.getElementById(id+"Subnav");
var _1f=document.getElementsByTagName("header")[0];
var _20=document.getElementById("layoutContainers");
if(_1a){
if(_19==0){
if(i$.hasClass(_1b,"wpthemeNavOpened")){
i$.removeClass(_1b,"wpthemeNavOpened");
}else{
i$.addClass(_1b,"wpthemeNavOpened");
}
}
if(i$.hasClass(_1a,_14)){
if(id==_17&&_18){
this.mobileNavSideTogglingRoot=true;
_1a.style.top=_1f.offsetHeight+"px";
_1a.style.height=(window.innerHeight-_1a.offsetTop)+"px";
if(ibmCfg.themeConfig.isRTL){
_1a.style.right=(-_1a.offsetWidth)+"px";
}else{
_1a.style.left=(-_1a.offsetWidth)+"px";
}
this.mobileNavResizeBinding=i$.bindDomEvt(window,"resize",this.resizeMobileNavSide);
}
i$.removeClass(_1a,_14);
_1a.setAttribute("aria-expanded","true");
_1c.setAttribute("aria-label",_16);
_1c.title=_1d.innerHTML=_16;
if(id==_17&&_18){
_1f.style.position="fixed";
_1f.style.width="100%";
_20.style.paddingTop=_1f.offsetHeight+10+"px";
_1f.style.zIndex="9998";
_1f.style.top="0px";
this.animateMobileNavSidePanel(_1a,0,function(){
if(wptheme.mobileNavSideLastExpanded.length==0){
wptheme.mobileNavSideTogglingRoot=false;
}
});
}
if(_18){
if(id==_17){
for(var i=0;i<this.mobileNavSideLastExpanded.length;i++){
document.getElementById(this.mobileNavSideLastExpanded[i]).onclick.call();
}
}else{
this.mobileNavSideExpanded.push(id+"Link");
_1c.parentNode.parentNode.parentNode.onclick=_1c.onclick;
}
}
if(_1e&&_18){
var _21=document.getElementById(_17);
var _22=_21.parentNode;
if(_1e.parentNode!=_22){
_1e=_1e.parentNode.removeChild(_1e);
_22.appendChild(_1e);
}
var _23=Math.min((_19*70),Math.floor(window.innerWidth-_1e.offsetWidth)-1);
_1e.style.top=_21.offsetTop+"px";
_1e.style.height=(window.innerHeight-_1e.offsetTop)+"px";
if(this.mobileNavSideTogglingRoot){
if(ibmCfg.themeConfig.isRTL){
_1e.style.right=(-_1e.offsetWidth)+"px";
}else{
_1e.style.left=(-_1e.offsetWidth)+"px";
}
}else{
if(ibmCfg.themeConfig.isRTL){
_1e.style.right=(_23-_1e.offsetWidth)+"px";
}else{
_1e.style.left=(_23-_1e.offsetWidth)+"px";
}
}
i$.removeClass(_1e,_14);
this.animateMobileNavSidePanel(_1e,_23,function(){
if(wptheme.mobileNavSideTogglingRoot&&id+"Link"==wptheme.mobileNavSideLastExpanded[wptheme.mobileNavSideLastExpanded.length-1]){
wptheme.mobileNavSideTogglingRoot=false;
}
});
}
}else{
if(_18){
if(id==_17){
this.mobileNavSideTogglingRoot=true;
this.mobileNavSideLastExpanded=this.mobileNavSideExpanded.slice(0);
for(var i=this.mobileNavSideExpanded.length-1;i>=0;i--){
document.getElementById(this.mobileNavSideExpanded[i]).onclick.call();
}
}else{
var _24=id+"Link";
var i=-1;
for(var j=this.mobileNavSideExpanded.length-1;j>=0;j--){
if(this.mobileNavSideExpanded[j]==_24){
i=j;
break;
}
}
if(i!=-1){
var _25;
for(var j=this.mobileNavSideExpanded.length-1;j>=i;j--){
_25=this.mobileNavSideExpanded.pop();
if(_24!=_25){
document.getElementById(_25).onclick.call();
}
}
}
_1c.parentNode.parentNode.parentNode.onclick=null;
}
}
if(_1e&&_18){
var _23=_1e.offsetLeft;
if(ibmCfg.themeConfig.isRTL){
_23=parseInt(_1e.style.right);
}
this.animateMobileNavSidePanel(_1e,_23-_1e.offsetWidth,function(){
i$.addClass(_1e,_14);
if(_1e.parentNode!=_1a){
_1e=_1e.parentNode.removeChild(_1e);
_1a.appendChild(_1e);
}
});
}
if(id==_17&&_18){
_1f.style.position="static";
_20.style.paddingTop="";
_1f.style.width="auto";
_1f.style.zIndex="auto";
this.animateMobileNavSidePanel(_1a,-_1a.offsetWidth,function(){
i$.addClass(_1a,_14);
_1a.setAttribute("aria-expanded","false");
_1c.setAttribute("aria-label",_15);
_1c.title=_1d.innerHTML=_15;
wptheme.mobileNavSideTogglingRoot=false;
});
i$.unbindDomEvt(this.mobileNavResizeBinding);
this.mobileNavResizeBinding=null;
}else{
i$.addClass(_1a,_14);
_1a.setAttribute("aria-expanded","false");
_1c.setAttribute("aria-label",_15);
_1c.title=_1d.innerHTML=_15;
}
}
}
},toggleMobileTopNav:function(_26,_27){
var _28=document.getElementById("wpthemeTopNavToggleBtn");
var _29=document.getElementById("wpthemeTopNavToggleBtnAccess");
var _2a=document.getElementsByTagName("header")[0];
var _2b=_2a.children[0];
if(i$.hasClass(_28,"wpthemeTopNavOpened")){
_2b.style.display="none";
i$.removeClass(_28,"wpthemeTopNavOpened");
_28.setAttribute("aria-label",_26);
_28.title=_29.innerHTML=_26;
}else{
_2b.style.display="block";
i$.addClass(_28,"wpthemeTopNavOpened");
_28.setAttribute("aria-label",_27);
_28.title=_29.innerHTML=_27;
}
this.resizeMobileNavSide();
}});
})();

