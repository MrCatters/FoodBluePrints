import React from "react";
import { useState } from "react";
import "./detailedpopup.css";

function DetailedPopup(props) {
    
    const closeHandler = () => {
        props.setTrigger2(false);
        
    }

    return (props.trigger2) ? (
        <div className="DetailedPopup">
            <div className="DetailedPopup-inner">
                <button onClick={ closeHandler }>close</button>
            </div>
        </div>
    ) : ""; 
}

export default DetailedPopup