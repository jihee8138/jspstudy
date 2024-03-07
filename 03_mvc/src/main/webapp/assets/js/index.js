/**
 * 
 */

const getContextPath = ()=> {
	const host = location.host; /* http://localhost:8080 */
	const url = location.href; /* http://localhost:8080/mvc/getDate.do */
	const begin = url.indexOf(host) + host.length;
	const end = url.indexOf('/', begin + 1);
	return url.substring(begin, end)
}


 const getDateTime = ()=> {
	 const type = document.getElementById('type');
	 if(type.value === 'date') {
	 /* type의 선택된 값이 value와 같다면 */	
	 	location.href = getContextPath() + '/getDate.do';
	 } else if(type.value === 'time') {
		location.href = getContextPath() + '/getTime.do';
	 } else if(type.value === 'datetime') {
	 	location.href= getContextPath() + '/getDateTime.do';		 
	 }
 }
 
 document.getElementById('btn').addEventListener('click', getDateTime);