import './App.css';
import { useState } from 'react';
import Header from './components/header/header';
import CommitList from './components/commit-list/commit-list';
import React from 'react';

export default function App() {
  const [items, setItems] = useState([
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "javascript",
          amount: 11234,
        }, 
        {
          language: "c",
          amount: 11234,
        }, 
        {
          language: "c++",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
    {
      author: "atyranski",
      creationDate: "2022-03-03",
      language: [
        {
          language: "java",
          amount: 11234,
        }, 
        {
          language: "python",
          amount: 5121
        }
      ],
      sha: "5706dded215f0651850b024539439ea0b29a0bd9",
      url: "https://api.github.com/repos/atyranski/agh-sr-2023/commits/5706dded215f0651850b024539439ea0b29a0bd9",
      message: "possible to send one message one way",
      repositoryFullName: "atyranski/agh-sr-2023"
    },
  ]);

  return (
    <div className="App">
      <div className="main-container">
        <Header setItems={setItems}/>
        <CommitList items={items}/>
      </div>
    </div>
  );
}
