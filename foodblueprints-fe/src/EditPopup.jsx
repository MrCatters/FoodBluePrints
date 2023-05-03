import React from "react";
import { useState } from "react";
import { useAuthHeader } from "react-auth-kit";
import axios from "axios";
import './popup.css';

function EditPopup(props) {
    
    const [title, setTitle] = useState(props.parentProps.title);
    const [content, setContent] = useState(props.parentProps.content);
    const [image, setImage] = useState(props.parentProps.img);
    const closeHandler = () => {

        setTitle(props.parentProps.title);
        setContent(props.parentProps.content);
        setImage(props.parentProps.img);
        props.setTrigger(false)
    }
    const submitHandler = (e) => {
        e.preventDefault()
        console.log(props.parentProps.content)
        //console.log(cookie);
        
        const cookiearray = document.cookie.split('=')
        const cookietemp = (cookiearray[1]);
        const cookie = (cookietemp.split(';'))[0]    
        
        axios.patch("http://127.0.0.1:8080/api/v1/recipe/patch_recipe", {
            "id":props.parentProps.id,
            "name":title,
            "contents":content,
            "image": props.parentProps.img
        }, {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${cookie}`
            }
        })
        .then(function(response) {
            console.log(response);
            props.setTrigger(false);
            window.alert("recipe succesffuly edited")
            setTitle("");
            setContent("");
            setImage("");
        })
        .catch(function(error) {
            console.log(error)
        });

        props.setTrigger(false)
        window.location.reload();
    
    }
    return (props.trigger) ? ( 
        <div className="popup">
            <div className="popup-inner">
                <div className="closeContainer">
                    <button className="closeAppPopup" onClick={ closeHandler }>✕</button>
                </div>
                <h2>Edit a recipe</h2>
                <form onSubmit={ submitHandler }>
                    <input type = "text" id = "title" value = {title} required onChange = {(e) => setTitle(e.target.value)} placeholder="Title of recipe..."></input>
                    <textarea id = "content" value = {content} required onChange={(e) => setContent(e.target.value)} placeholder="enter recipe here.."></textarea>
                    <button className = "submit" type="submit" >Submit</button>
                </form>
            </div>
        </div>
    ) : "";
}

export default EditPopup