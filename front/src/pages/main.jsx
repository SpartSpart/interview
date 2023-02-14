import {Link} from "react-router-dom";
import styles from '../css/main.module.css'

function Main() {

    return (
        <>
            <h1 className={styles.h1}>Main</h1>
            <Link to={'/'}>
                <button> Main</button>
            </Link>
            <Link to={'/new/interview'}>
                <button> New interview</button>
            </Link>
            <Link to={'/report'}>
                <button> Report</button>
            </Link>
        </>
    );
}

export default Main;
