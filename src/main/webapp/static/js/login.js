var employee=null;
var customer=null;
//var baseURL="/BookStore/";
var baseURL ="/Project1/";

//date = new Date(); Method for testing if date is within 7 days of now
//date.setDate(date.getDate() + 7);
//date2 = new Date();
//date2.setDate(date2.getDate() + 8);
//if(date2>date) console.log('today is too late');


//send = new Object();
//send["subSub"] = employee.id;
//if(employee.managerObj!=null)
//send["subFirstMan"] = employee.managerObj.id;
//if(employee.headObj!=null)
//send["subHead"] = employee.headObj.id;
//send["subName"] = document.getElementById("subTitle").value;
//send["subLocation"] = document.getElementById("subLocation").value;
//send["subCost"] = document.getElementById("subCost").value;
//send["subJustification"] = document.getElementById("subJustification").value;
//send["subMissedHours"] = document.getElementById("subMissedHours").value;
//send["subType"] = document.getElementById("subType").value;
//if(document.getElementById("subDate").value>"2019-06-03"){
//	let dateS = document.getElementById("subDate").value;
//	let timeS = document.getElementById("subTime").value;
//	let dateTimeS = dateS.substring(5,7) + "/" + dateS.substring(8,10) + "/" + dateS.substring(0,4) + "  " + timeS;
//	send["subDate"] = dateTimeS;
//	console.log(dateTimeS);
//}

window.onload = function () {
    addNavBar();
    document.getElementById("submitReimbursement").addEventListener("click",attemptSubmit);
    
}

function attemptSubmit(){
	let send = new Object();
	console.log("pressed");
	if(employee!=null){
		send["subSub"] = employee.id;
		if(employee.managerObj!=null)
		send["subFirstMan"] = employee.managerObj.id;
		if(employee.headObj!=null)
		send["subHead"] = employee.headObj.id;
		send["subName"] = document.getElementById("subTitle").value;
		send["subLocation"] = document.getElementById("subLocation").value;
		send["subCost"] = document.getElementById("subCost").value;
		send["subJustification"] = document.getElementById("subJustification").value;
		send["subMissedHours"] = document.getElementById("subMissedHours").value;
		send["subType"] = document.getElementById("subType").value;
		if(send.subMissedHours==null)send.subMissedHours = 0;
		if(send.subMissedHours>40)send.subMissedHours = 40;
		if(send.subMissedHours<0)send.subMissedHours = 0;
		send["subDate"]=""
		if(document.getElementById("subDate").value){
			let dateS = document.getElementById("subDate").value;
			let timeS = document.getElementById("subTime").value;
			let dateTimeS = dateS.substring(5,7) + "/" + dateS.substring(8,10) + "/" + dateS.substring(0,4) + "  " + timeS;
			send["subDate"] = dateTimeS;
			
		}
		console.log(send.subDate);
		console.log(send);
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange=submitSuccess;
	//	baseURL += 'login'
		xhttp.open("POST", "/Project1/submission");
		console.log("/Project1/submitReimbursement");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
		console.log("subSub: "+send.subSub + " subFirstMan: " + send["subFirstMan"]);
		
		let sendStr = "" +"subSub="+send.subSub+"&subFirstMan="+send["subFirstMan"]+"&subHead="+send["subHead"]
		+"&subName="+send["subName"]+"&subLocation="+send["subLocation"]+"&subCost="+send["subCost"]
		+"&subJustification="+send["subJustification"]+"&subMissedHours="+send["subMissedHours"]+"&subType="+send["subType"]
		+"&subDate="+send["subDate"];
		xhttp.send(sendStr);
		
		function submitSuccess () {
			if(xhttp.readyState===4 && xhttp.status===200) {
				document.getElementById("subTitle").value = "";
				document.getElementById("subLocation").value = "";
				document.getElementById("subCost").value = 1;
				document.getElementById("subJustification").value = "";
				document.getElementById("subMissedHours").value = 0;
				document.getElementById("subType").value =100;
				let fDate = new Date();
				fDate.setDate(fDate.getDate() + 7);
				document.getElementById("subDate").value = fDate.toISOString().substring(0,10);
				document.getElementById("subTime").value = "15:00";
			}
		}
	}
}


var navbar = `
<div>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
    <a class="navbar-brand" href="#">RMS</a>
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      <li class="nav-item">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Disabled</a>
      </li>
    </ul>
    <div class="navbar-nav" id="authent"> ReWrite me
    </div>
</nav>
</div>`;
 
 
var unauthenticated = `

	      <input class="form-control mr-sm-2" type="text" placeholder="Username" id="username">
	      <input class="form-control mr-sm-2" type="password" placeholder="password" id="password">
	      <button class="btn btn-outline-success my-2 my-sm-0" id="login">Login</button>

        `;

var authenticated = `

	<ul class="navbar-nav mt-2 mt-lg-0">
	<li class="nav-item">
		<div class="navbar-text">Welcome: </div>
	</li>
	<li class="nav-item pl-2">
		<span class="navbar-text" id="authUserName1"></span>
	</li>
	<li class="nav-item pl-2 pr-2">
		<span class="navbar-text" id="authUserName2"></span> 
	</li>
	</ul>

	<button class="btn btn-outline-danger" id="logout">Logout</button>
    `;



function addNavBar(){
	let div = document.createElement("div");
	div.innerHTML=navbar;
	let body = document.getElementsByTagName("body")[0];
	body.insertBefore(div,body.childNodes[0]);
	
	document.getElementById("authent").innerHTML=unauthenticated;
	
	//add event listeners
	document.getElementById("login").addEventListener("click",authenticate);
	document.getElementById("password").onkeydown=checkPasswordEnter;
	//authenticate();
}

function checkPasswordEnter(){
	if(event.which===13)
		authenticate();
}


function authenticate() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange=loginSuccess;
//	baseURL += 'login'
	xhttp.open("POST", "/Project1/login");
	console.log(baseURL);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	let user = document.getElementById("username").value;
	//console.log(user);
	let pass = document.getElementById("password").value;
//	console.log("user: "+document.getElementById("username").value + " pw: " + document.getElementById("password").value);
	console.log("user: "+user + " pw: " + pass);
	xhttp.send("user="+user+"&pass="+pass);
	function loginSuccess() {
		if(xhttp.readyState===4 && xhttp.status===200) {
			var data = JSON.parse(xhttp.responseText);
			employee=data.employee;
			//customer=data.customer;
			console.log(baseURL);
			console.log(employee);
			document.getElementById("authent").innerHTML=authenticated;
			document.getElementById("logout").addEventListener("click",logout);
//			console.log(employee);
				document.getElementById("authUserName1").innerHTML=employee.firstName+" "+ employee.lastName+ ", ";
				document.getElementById("authUserName2").innerHTML=employee.title;
				let btns = document.getElementsByClassName("emp-btn");
				for(let i=0; i<btns.length; i++){
					btns[i].disabled=false;
				}
		}
	}
}

function logout() {
	console.log("logging out");
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange=finish;
	xhttp.open("DELETE", "/Project1/login");
	xhttp.send();
	
	function finish(){
		if(xhttp.readyState===4){
			document.getElementById("authent").innerHTML=unauthenticated;
			let btns = document.getElementsByClassName("emp-btn");
			for(let i = 0; i<btns.length; i++){
				btns[i].disabled=true;
			}
			document.getElementById("login").addEventListener("click",authenticate);
			document.getElementById("password").onkeydown=checkPasswordEnter;
		}
	}
}