import React, { useState } from "react";
import "./recipecard.css";
import axios from "axios";
import EditPopup from "./EditPopup.jsx"



function RecipeCard(props) {
    const [popup, setPopup] = useState();
    const [globalId, setGlobalId] = useState();

    function editHandler(e) {
        e.preventDefault()
        setPopup(true)
        
    }

    function deleteHandler(e,id) {
        e.preventDefault()
        const cookiearray = document.cookie.split('=')
        const cookietemp = (cookiearray[1]);
        const cookie = (cookietemp.split(';'))[0]   

        let data = JSON.stringify({
        "recipeId": `${id}`
        });

        let config = {
        method: 'delete',
        maxBodyLength: Infinity,
        url: 'http://127.0.0.1:8080/api/v1/recipe/delete_recipe',
        headers: { 
            'Content-Type': 'application/json', 
            'Authorization': `Bearer ${cookie}`
        },
        data : data
        };

        axios.request(config)
        .then((response) => {
        console.log(JSON.stringify(response.data));
        window.location.reload();
        })
        .catch((error) => {
        console.log(error);
        });

        /*
        e.preventDefault()
        const cookiearray = document.cookie.split('=')
        const cookietemp = (cookiearray[1]);
        const cookie = (cookietemp.split(';'))[0]   

        axios.delete("http://127.0.0.1:8080/api/v1/recipe/delete_recipe", {
           
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        }, {
            data: {
                "recipeId":id    
            }
           
        })
        .then(function (response) {
            window.location.reload();
        })
        .then(function (error) {
            console.log(error);
        });
        */
    }
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
                <button onClick = { (e) => editHandler(e) }  className="edit">Edit</button>
                <button onClick={ (e) => {deleteHandler(e,props.id)} } className="delete">Delete</button>
            </div>
            <EditPopup className="editpop" trigger = { popup } setTrigger = {setPopup} id = {props.id} parentProps = {props}>

            </EditPopup>
        </div>  
    );
}

export default RecipeCard