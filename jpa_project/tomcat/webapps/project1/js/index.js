$(document).ready(function() {
  
  $('#signIn').click(function(){
    var username = document.getElementById('usernameLogin').value
    var password = document.getElementById('passwordLogin').value 
     console.log("signin fine");
  
    reqwest({
      url: '/loginProject1', 
      method: 'post', 
      data: { 
        username:username, 
        password: password, 
      }, 
      success: function(resp){
        if(resp.status == 202) {
          alert( "Failed to login.");
        } else {
          alert("Congratulations!!!!!!.You are logged in.");
        }
    
      },
    })
  })
  $('#signUp').click(function(){
    var username = document.getElementById('usernameSignUp').value
    var email = document.getElementById('emailSignUp').value
    var password = document.getElementById('passwordSignUp').value   

    reqwest({
      url: '/signUpProject1', 
      method: 'post', 
      data: { 
        username:username, 
        password: password,
        email:email 
      }, 
      success: function(resp){
        if(resp.status == 202) {
          alert( "Username already used.");        
        } else {
          alert("Successfully created an account."); 
        }      
        
      },
    })

  })

})
