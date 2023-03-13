import './App.css';
import { useState } from 'react';
import Header from './components/header/header';
import CommitList from './components/commit-list/commit-list';
import React from 'react';

export default function App() {
  const [items, setItems] = useState([])
  // const [items, setItems] = useState([
  //   {
  //     "author": "Arkadiusz Tyrański",
  //     "creationDate": "2023-02-01T18:22:22",
  //     "sha": "4ab763f16d1e0f9eceecd804b5b9a669aa7c3290",
  //     "url": "https://api.github.com/repos/atyranski/agh-iot-2022/commits/4ab763f16d1e0f9eceecd804b5b9a669aa7c3290",
  //     "message": "Update README.md",
  //     "repositoryFullName": "atyranski/agh-iot-2022",
  //     "stats": {
  //       "total": 1,
  //       "added": 1,
  //       "removed": 0,
  //       "modified": 1,
  //       "renamed": 2,
  //       "copied": 3,
  //       "changed": 1,
  //       "unchanged": 0
  //     },
  //     "files": [
  //         {
  //             "filename": "README.md",
  //             "status": "modified",
  //             "additions": 1,
  //             "deletions": 0,
  //             "changes": 1
  //         },
  //         {
  //           "filename": "README.md",
  //           "status": "modified",
  //           "additions": 1,
  //           "deletions": 0,
  //           "changes": 1
  //         },
  //         {
  //           "filename": "README.md",
  //           "status": "modified",
  //           "additions": 1,
  //           "deletions": 0,
  //           "changes": 1
  //         },
  //         {
  //           "filename": "README.md",
  //           "status": "modified",
  //           "additions": 1,
  //           "deletions": 0,
  //           "changes": 1
  //         },
  //         {
  //           "filename": "README.md",
  //           "status": "modified",
  //           "additions": 1,
  //           "deletions": 0,
  //           "changes": 1
  //         },
  //         {
  //           "filename": "README.md",
  //           "status": "modified",
  //           "additions": 1,
  //           "deletions": 0,
  //           "changes": 1
  //         }
  //     ]
  // }
  // ]);

  return (
    <div className="App">
      <div className="main-container">
        <Header setItems={setItems}/>
        <CommitList items={items}/>
      </div>
    </div>
  );
}
