function getLabelID(array, type){
   var num = 0;
   var datetime_ID = new Array();
   if (type == "year") {
      var flag = "0000";
   }
   else if (type == "date") {
      var flag = "00-00";
   }
   for(var i = 0; i < array.length; i ++) {
      if (flag == array[i]) {
	 continue;
      }
      else {
	 flag = array[i];
	 datetime_ID[num] = i;
	 num ++;
      }
   }
   return datetime_ID;
}
// 下标组成的数组
function make_array_by_id(len) {
   var I = new Array();
   for (var i = 0; i < len; i ++) {
      I[i] = i;
   }
   return I;
}
// 数组的减法，A - B
function array_substraction(A, B) {
   var flag = 0;
   var D = new Array();
   for (var i = 0; i < A.length; i ++) {
      for (var j = 0; j < B.length; j ++) {
	  if (A[i] == B[j]) {
	     break;
	  }
	  else {
	     if (j == B.length - 1) {
		D[flag] = A[i];
		flag ++;
		break;
	     }
	     else {
		continue;
	     }
	  }
      }
   }
   return D;
}
//================================ 处理时间=================================
function SameYearSameDate(result, history_datetime_array){
   // 默认 result 不为空
   for (var i = 1; i < result.length; i ++) {
      history_datetime_array[i] = history_datetime_array[i].slice(5); // omit 掉年的部分
      history_datetime_array[i] = history_datetime_array[i].slice(6);
   }
}

function SameYearDiffDate(result, history_datetime_array, date_array) {
   var special_date_array = new Array();
   special_date_array = getLabelID(date_array, "date");
   var I = new Array();
   I = make_array_by_id(result.length);
   var D_date = new Array();
   D_date = array_substraction(I, special_date_array);
   for (var i = 1; i < result.length; i ++) {
      history_datetime_array[i] = history_datetime_array[i].slice(5);
   }
   for (var j = 0; j < D_date.length; j ++) {
       history_datetime_array[D_date[j]] = history_datetime_array[D_date[j]].slice(6);
   }
}

function DiffYearDiffDate(result, history_datetime_array, year_array, date_array) {
   // 将年划分为多个数组，每个数组中的年都相同
   var special_year_array = new Array();
   var special_date_array = new Array();
   special_year_array = getLabelID(year_array, "year");
   special_date_array = getLabelID(date_array, "date");
   var I = new Array();
   I = make_array_by_id(result.length);
   var D_year = new Array();
   var D_date = new Array();
   D_year = array_substraction(I, special_year_array);
   D_date = array_substraction(I, special_date_array);
   for (var j = 0; j < D_year.length; j ++) {
       history_datetime_array[D_year[j]] = history_datetime_array[D_year[j]].slice(5);
   }
   // 年不同的，月日一定不同
   for (var j = 0; j < D_date.length; j ++) {
       history_datetime_array[D_date[j]] = history_datetime_array[D_date[j]].slice(6);
   }
}

function SameYear(result, history_datetime_array) {
   for (var i = 1; i < result.length; i ++) {
      history_datetime_array[i] = history_datetime_array[i].slice(5);
   }
}

function DiffYear(result, history_datetime_array, year_array) {
   var special_year_array = new Array();
   special_year_array = getLabelID(year_array, "year");
   var I = new Array();
   I = make_array_by_id(result.length);
   var D_year = new Array();
   D_year = array_substraction(I, special_year_array);
   for (var i = 0; i < result.length; i ++) {
      for (var j = 0; j < D_year.length; j ++) {
	 history_datetime_array[D_year[j]] = history_datetime_array[D_year[j]].slice(5);
      }
   }
}

