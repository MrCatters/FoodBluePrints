import React from "react";
import { useState } from "react";
import { ReactMarkdown } from "react-markdown/lib/react-markdown";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faX } from '@fortawesome/free-solid-svg-icons'
import "./detailedpopup.css";

function DetailedPopup(props) {
    
    const closeHandler = () => {
        props.setTrigger2(false);
        
    }

    return (props.trigger2) ? (
        <div className="DetailedPopup">
            <div className="DetailedPopup-inner">
            <button onClick={ closeHandler }><FontAwesomeIcon icon={faX} /></button> 
                <div className="horizontal-container">
                    <img src={props.converted}></img>
                    <div className="contents">
                       
                        <h2>{props.parentProps.title}</h2>
   
                     
                       
                        <h3>{props.parentProps.author}</h3>
                        <ReactMarkdown children= {props.parentProps.content}></ReactMarkdown>            
                        
                    </div>
                </div>
                
            </div>
        </div>
    ) : ""; 
}

export default DetailedPopup