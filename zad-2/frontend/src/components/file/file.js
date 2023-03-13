import React from 'react';
import './file.css';

export default function File({file}) {
    return (
        <div className='file'>
            <h5>{file.filename}</h5>
            <div className='stats'>
                <h5 className='additions'> +{file.additions}</h5>
                <h5 className='changes'> {file.changes}</h5>
                <h5 className='deletions'> -{file.deletions}</h5>
            </div>
        </div>
    );
}