import React, { useState, useEffect } from "react";
import "./recipecard.css";
import axios from "axios";
import EditPopup from "./EditPopup.jsx";
import ReactMarkdown from "react-markdown";
import DetailedPopup from "./DetailedPopup.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faHeart} from '@fortawesome/free-solid-svg-icons';
import {faHeart as farHeart} from '@fortawesome/free-regular-svg-icons';




function RecipeCard(props) {
    
    const [popup, setPopup] = useState();
    const [detailActive, setDetailActive] = useState(false);
    const [isFavorite, setIsFavorite] = useState(false);

    const cookiearray = document.cookie.split('=')
    const cookietemp = (cookiearray[1]);
    const cookie = (cookietemp.split(';'))[0]; 

    const converted = `data:image/png;base64, ${props.img}`
   

    function favoriteHandler() {
        let data = JSON.stringify({
            "searchString": props.id
          });
          
          let config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: 'http://127.0.0.1:8080/api/v1/recipe/post_favorite_recipe',
            headers: { 
              'Content-Type': 'application/json', 
              'Authorization': `Bearer ${cookie}`
            },
            data : data
          };
          
          axios.request(config)
          .then((response) => {
            
            console.log(props.pingd)
            console.log(JSON.stringify(response.data));
          })
          .catch((error) => {
            console.log(error);
          });
          window.location.reload();
         
         
    }

    function unfavoriteHanlder() {
        let data = JSON.stringify({
            "searchString": props.id
          });
          
          let config = {
            method: 'delete',
            maxBodyLength: Infinity,
            url: 'http://127.0.0.1:8080/api/v1/recipe/delete_favorite_recipe',
            headers: { 
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${cookie}`
            },
            data : data
          };
          
          axios.request(config)
          .then((response) => {
            console.log(JSON.stringify(response.data));
          })
          .catch((error) => {
            console.log(error);
          });
          window.location.reload();
          
         
          
    }
    useEffect((e) => {
        props.favorites.forEach(idt => {
            if (props.id === idt) {
                setIsFavorite(true);
            }
        })
    }, [props.favorites, props.id]);

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
    }


    return (isFavorite) ?(
        <div className="recipe-card">
            <div className="top-container">
                <div className="header-container">
                        <h3 className="title">{props.title}</h3>
                        <FontAwesomeIcon onClick={ e => {unfavoriteHanlder()}} icon={faHeart} />
                </div>
                <p className="content">
                    <img src = {converted} alt = "img"></img>
                    
                </p>    
            </div>
           
            <div className="footer">
                <button onClick = { (e) => editHandler(e) }  className="edit">Edit</button>
                
                <button onClick={e => {setDetailActive(true)}} className="expand">View</button>
                <button onClick={ (e) => {deleteHandler(e,props.id)} } className="delete">Delete</button>
            </div>
            
            <EditPopup className="editpop" trigger = { popup } setTrigger = {setPopup} id = {props.id} parentProps = {props}>
            </EditPopup>
            <DetailedPopup className = "detailpop" trigger2 = { detailActive } setTrigger2 = {setDetailActive} parentProps = {props} converted = {converted}>
            </DetailedPopup>
        </div>  
    ) : 
    <div className="recipe-card">
            <div className="top-container">
                <div className="header-container">
                    <h3 className="title">{props.title}</h3>
                    <FontAwesomeIcon onClick={ e => {favoriteHandler()}} icon={farHeart} />
                </div>
                <p className="content">
                    <img src = {converted} alt = "img"></img>
                    
                </p>    
            </div>
           
            <div className="footer">
                <button onClick = { (e) => editHandler(e) }  className="edit">Edit</button>
                
                <button onClick={e => {setDetailActive(true)}} className="expand">View</button>
                <button onClick={ (e) => {deleteHandler(e,props.id)} } className="delete">Delete</button>
            </div>
            
            <EditPopup className="editpop" trigger = { popup } setTrigger = {setPopup} id = {props.id} parentProps = {props}>
            </EditPopup>
            <DetailedPopup className = "detailpop" trigger2 = { detailActive } setTrigger2 = {setDetailActive} parentProps = {props} converted = {converted}>
            </DetailedPopup>
        </div>  ;
}

export default RecipeCard