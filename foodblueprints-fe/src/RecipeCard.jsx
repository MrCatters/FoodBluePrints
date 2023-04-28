import React from "react";
import "./recipecard.css";
import axios from "axios";

function editHandler(e) {
    e.preventDefault()
}

function deleteHandler(e,id) {
    e.preventDefault()
    const cookiearray = document.cookie.split('=')
    const cookietemp = (cookiearray[1]);
    const cookie = (cookietemp.split(';'))[0]   
    axios.delete("http://127.0.0.1:8080/api/v1/recipe/delete_recipe", {
        "id":id
    }, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${cookie}`
        }
    })
    .then(function (response) {
        console.log(response);
    })
    .then(function (error) {
        console.log(error);
    });
}

function RecipeCard(props) {
    return (
        <div className="recipe-card">
            <div className="header-container">
                <h3 className="title">{props.title}</h3>
                <h4 className="author">{props.author}</h4>
            </div>
            <p className="content">
                {props.content}
            </p>
            <div className="footer">
                <button onClick = { editHandler }  className="edit">Edit</button>
                <button onClick={ (e) => {deleteHandler(e,props.id)} } className="delete">Delete</button>
            </div>
            
        </div>  
    );
}

export default RecipeCard