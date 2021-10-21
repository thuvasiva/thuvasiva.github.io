function clearWarning(e){
   let confirmation = confirm("Are you sure you want to clear?");
   if(!confirmation){
       e.preventDefault();
   }
}

function emptyFields(e){
    if(document.getElementById("title").value == ""){
        document.getElementById("title").setAttribute('style','background-color:crimson');
        e.preventDefault();
    }
    if(document.getElementById("maintext").value == ""){
        document.getElementById("maintext").setAttribute('style','background-color:crimson');
        e.preventDefault();
    }
        
}

function changeTitle(){
    document.getElementById("title").setAttribute('style','background-color:white');
}

function changeMainText(){
    document.getElementById("maintext").setAttribute('style','background-color:white');
}

function preview(){
    let titleValue = document.getElementById('title').value;
    let maintextValue = document.getElementById('maintext').value;
    localStorage.setItem('title',titleValue);
    localStorage.setItem('maintext',maintextValue);
    window.location.href = "http://cakephp-mysql-persistent-myfirstproject.apps.okd.eecs.qmul.ac.uk/thuvaragan_miniproject/TestBlog.php";
}

let titleFromStorage = localStorage.getItem('title');
let maintextFromStorage = localStorage.getItem('maintext');

if(localStorage.getItem('title') != null & localStorage.getItem('maintext')!= null){
document.getElementById('title').value= titleFromStorage;
document.getElementById('maintext').value= maintextFromStorage;
}

localStorage.clear();