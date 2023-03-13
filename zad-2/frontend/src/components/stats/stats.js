import React from 'react';
import './stats.css';

export default function Stats({stats}) {
    return (
        <div className='stats'>
            <div className='added' style={{flexGrow: stats.added}}>
                {stats.added}
            </div>
            <div className='modified' style={{flexGrow: stats.modified}}>
                {stats.modified}
            </div>
            <div className='renamed' style={{flexGrow: stats.renamed}}>
                {stats.renamed}
            </div>
            <div className='copied' style={{flexGrow: stats.copied}}>
                {stats.copied}
            </div>
            <div className='changed' style={{flexGrow: stats.changed}}>
                {stats.changed}
            </div>
            <div className='unchanged' style={{flexGrow: stats.unchanged}}>
                {stats.unchanged}
            </div>
            <div className='removed' style={{flexGrow: stats.removed}}>
                {stats.removed}
            </div>
        </div>
    );
}