import React from "react";
import Header from "./Header";
import RecipeCard from "./RecipeCard";
import axios from "axios"
import "./profile.css"

function Profile() {
    let userEmail;
    let recipes;
    const cookiearray = document.cookie.split('=')
    const cookietemp = (cookiearray[1]);
    const cookie = (cookietemp.split(';'))[0] 
    
    axios.get("http://127.0.0.1:8080/api/v1/user/user_information",{
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${cookie}`
        }
    })
    .then(function(response) {
        console.log(response)
        userEmail = response.data.email
        console.log(userEmail)
    }).catch(function (error) {
        console.log(error)
    });

    axios.get("http://127.0.0.1:8080/api/v1/recipe/user_email_recipes", {
        "searchString": `${userEmail}`
    }, 
    {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${cookie}`
        }
    })
    .then(function(response){
        console.log("---success----")
        console.log(response)
    })
    .catch(function(error){
        console.log("---fail----")
        console.log(error)
    }); 

    return (
        
        <div className="profile">
            <Header />
            <div className="container">
                <h1>Welcome back, Joe</h1>
                <h2>Your Recipies</h2>
                <RecipeCard title="recipe 1" author="author 1" content="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tellus mauris a diam maecenas sed enim ut sem viverra. Id diam maecenas ultricies mi eget mauris pharetra et. Egestas congue quisque egestas diam in. In nulla posuere sollicitudin aliquam ultrices sagittis orci a. Dui id ornare arcu odio ut sem nulla pharetra. Ac odio tempor orci dapibus ultrices. Sed adipiscing diam donec adipiscing. Tincidunt eget nullam non nisi est sit amet facilisis. Diam quam nulla porttitor massa. Tellus elementum sagittis vitae et leo. Vel turpis nunc eget lorem dolor sed viverra ipsum."></RecipeCard>
                
                <RecipeCard title="recipe 2" author="author 2" content="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Faucibus turpis in eu mi. At imperdiet dui accumsan sit. Id interdum velit laoreet id donec ultrices. Etiam tempor orci eu lobortis elementum. Dignissim convallis aenean et tortor. Luctus venenatis lectus magna fringilla urna. Condimentum lacinia quis vel eros donec. Nisi quis eleifend quam adipiscing vitae proin sagittis nisl. Neque laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt. Gravida cum sociis natoque penatibus et magnis. Dui accumsan sit amet nulla. Elementum tempus egestas sed sed. Ornare suspendisse sed nisi lacus sed viverra. Cras pulvinar mattis nunc sed. Eget velit aliquet sagittis id consectetur purus ut faucibus pulvinar. Morbi quis commodo odio aenean sed. Fusce id velit ut tortor. Risus commodo viverra maecenas accumsan lacus."></RecipeCard>
     
            </div>
           
        </div>
    );
}

export default Profile