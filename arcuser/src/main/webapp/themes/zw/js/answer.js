setInterval("times()",1000);

/*----------计时开始------------*/
var m=0,h=0,s=0
function times(){
if(s>0 && (s%60)==0){m+=1;s=0;}
if(m>0 && (m%60)==0){h+=1;m=0;}
t=h+":"+m+":"+s;
document.getElementById("kstime").innerHTML=t;
s++;
}
