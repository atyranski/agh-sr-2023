import File from 'components/file/file';
import Stats from 'components/stats/stats';
import React from 'react';
import './commit.css';

export default function Commit({item}) {
    return (
        <div className='commit'>
            <h2>{item.repositoryFullName}</h2>
            <h3 className='sub-title'>
                <span>sha: </span>
                <strong>{item.sha}</strong>
            </h3>

            <h4>Account: <span>{item.author}</span></h4>
            <h4>Creation date: <span>{item.creationDate}</span></h4>
            <h4>Commit message: <span>{item.message}</span></h4>

            <h4>Stats: 
                <Stats stats={item.stats} />
            </h4>

            <h4>Files: 
                <div className='file-list'>
                    {item.files && item.files.length > 0 && item.files.map((fileObject) => (
                        <File key={fileObject.filename} file={fileObject} />
                    ))}
                </div>
            </h4>
        </div>
    );
}