import React from "react";
import { useState } from "react";
import { useAuthHeader } from "react-auth-kit";
import axios from "axios"
import './popup.css'

function AddPopup(props) {
    const [title, setTitle] = useState();
    const [content, setContent] = useState();
    const [image, setImage] = useState();
    const placeholder_image = "iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAADMElEQVR4nOzVwQnAIBQFQYXff81RUkQCOyDj1YOPnbXWPmeTRef+/3O/OyBjzh3CD95BfqICMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMO0TAAD//2Anhf4QtqobAAAAAElFTkSuQmCC"
    
    const submitHandler = (e) => {
        e.preventDefault()
        //console.log(cookie);
         
        const cookiearray = document.cookie.split('=')
        const cookietemp = (cookiearray[1]);
        const cookie = (cookietemp.split(';'))[0]    
        
        axios.post("http://127.0.0.1:8080/api/v1/recipe/post_recipe", {
            "name":title,
            "contents":content,
            "image": placeholder_image
        }, {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function(response) {
            console.log(response);
            props.setTrigger(false);
            window.alert("recipe succesffuly submitted")
            setTitle("");
            setContent("");
            setImage("");
        })
        .catch(function(error) {
            console.log(error)
        });
        
    }
    return (props.trigger) ? ( 
        <div className="popup">
            <div className="popup-inner">
                <div className="closeContainer">
                    <button className="closeAppPopup" onClick={e => props.setTrigger(false)}>✕</button>
                </div>
                <h2>Create a new recipe!</h2>
                <form onSubmit={ submitHandler }>
                    <input type = "text" id = "title" required onChange = {(e) => setTitle(e.target.value)} placeholder="Title of recipe..."></input>
                    <textarea id = "content" required onChange={(e) => setContent(e.target.value)} placeholder="enter recipe here.."></textarea>
                    <button className = "submit" type="submit" >Submit</button>
                </form>
            </div>
        </div>
    ) : "";
}

export default AddPopup