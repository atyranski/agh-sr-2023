import React from 'react';
import './commit.css';

export default function Commit({item}) {
    return (
        <div className='commit'>
            <h2>{item.repositoryFullName}</h2>
            <h3 className='sub-title'>
                <span>sha: </span>
                <a href={item.url}>{item.sha}</a>
            </h3>

            <h4>Account: <span>{item.author}</span></h4>
            <h4>Creation date: <span>{item.creationDate}</span></h4>
            <h4>Commit message: <span>{item.message}</span></h4>

            <h4>Languages: 
                <div className='languages-list'>
                    {item.language && item.language.length > 0 && item.language.map((languageObject, index) => (
                        <div key={languageObject.language} className='language'>{languageObject.language}: <span>{languageObject.amount}</span></div>
                    ))}
                </div>
            </h4>
        </div>
    );
}