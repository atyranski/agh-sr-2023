import { useForm } from 'react-hook-form';
// import { useEffect } from 'react';
import './header.css';

export default function Header({setItems}) {
    const { register, handleSubmit, watch, formState: { errors } } = useForm();
    const onSubmit = data => {
        console.log(data)
        fetchData(data);
    }

    const fetchData = async (data) => {
        const url = "https://api.github.com/search/commits?q=author:" + data.account + 
                    "+author-date:" + data.dateFrom + ".." + data.dateTo + 
                    "&sort=author-date"
        const response = await fetch(url);
        const payload = await response.json();
        return setItems(payload.items);
      }
    
    // useEffect(() => {
    //     fetchData();
    // },[])

    return (
        <div className='header'>
            <h3>GitHub Commit Analyzer</h3>
            <form onSubmit={handleSubmit(onSubmit)}>
                <input placeholder='Account'    {...register("account", { required: true })} />
                <input placeholder='Repository' {...register("repo")} />
                <label>Date from: </label>
                <input type="date"   {...register("dateFrom", { required: true })} />
                <label>Date to: </label>
                <input type="date"   {...register("dateTo", { required: true })} />

                {errors.exampleRequired && <span>This field is required</span>}

                <input type="submit" />
            </form>
        </div>
    );
}