import React from 'react';
import Commit from '../commit/commit';
import './commit-list.css';

export default function CommitList({items}) {
    return (
        <div className='commit-list'>
            {items && items.length > 0 && items.map((itemObject, index) => (
                <Commit key={index} item={itemObject} />
              ))}
        </div>
    );
}