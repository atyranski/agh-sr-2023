import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faGithub } from '@fortawesome/free-brands-svg-icons'
import React from 'react';
import { useForm } from 'react-hook-form';

import './header.css';

export const GITHUB_ACCESS_TOKEN = process.env.REACT_APP_GITHUB_ACCESS_TOKEN;

export default function Header({setItems}) {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const onSubmit = data => {
        console.log(data)
        fetchData(data);
    }

    const fetchData = async (data) => {
        // const url = "https://api.github.com/search/commits?q=author:" + data.account + 
        //             "+author-date:" + data.dateFrom + ".." + data.dateTo + 
        //             "&sort=author-date"
        const url = "http://localhost:8080/search?account=" + data.account + 
                    "&from=" + data.dateFrom + "&to=" + data.dateTo

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": GITHUB_ACCESS_TOKEN
            }
        });
        const payload = await response.json();
        
        return setItems(payload.payload);
      }

    return (
        <div className='header'>
            <h3><FontAwesomeIcon icon={faGithub} /> GitHub Commit Analyzer</h3>
            <form onSubmit={handleSubmit(onSubmit)}>
                <input placeholder='Account' defaultValue={"atyranski"} {...register("account", { required: true })} />
                <label>Date from: </label>
                <input type="date" defaultValue={"2023-03-01"} {...register("dateFrom", { required: true })} />
                <label>Date to: </label>
                <input type="date" defaultValue={"2023-03-10"}   {...register("dateTo", { required: true })} />

                {errors.exampleRequired && <span>This field is required</span>}

                <input type="submit" />
            </form>
        </div>
    );
}