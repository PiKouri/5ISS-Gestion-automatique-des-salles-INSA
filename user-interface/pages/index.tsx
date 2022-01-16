import Head from 'next/head';
import React, { useState } from 'react';
import Links, { jsonFetcher } from '../constants/links';
import Room from '../components/Room';
import CentralManager from '../components/CentralManager';
import AddRoom from '../components/AddRoom';

async function componentDidMount(): Promise<number[]> {
  try {
    return await jsonFetcher(Links.rooms);
  } catch (e) {
    return [];
  }
}

export default function Home() {
  const [ids, setIds] = useState([]);
  // const ids = Array.from([0, 1, 2, 3]);
  React.useEffect(() => {
    // postRoom('Test').then(() => {
    //   // eslint-disable-next-line @typescript-eslint/no-empty-function
    // });
    componentDidMount().then((value) => {
      // eslint-disable-next-line react/no-direct-mutation-state
      setIds(value);
    });
  }, []);

  return (
    <div className="container">
      <Head>
        <title>Automatic Room Management</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main>
        <h1 className="title">Automatic Room Management</h1>

        <p className="description">
          Manage INSA automatic room management system 5ISS (HOK - FAURE)
        </p>

        {/*<div className="grid">*/}
        {/*  <a href="https://nextjs.org/docs" className="card">*/}
        {/*    <h3>Documentation &rarr;</h3>*/}
        {/*    <p>Find in-depth information about Next.js features and API.</p>*/}
        {/*  </a>*/}

        {/*  <a href="https://nextjs.org/learn" className="card">*/}
        {/*    <h3>Learn &rarr;</h3>*/}
        {/*    <p>Learn about Next.js in an interactive course with quizzes!</p>*/}
        {/*  </a>*/}

        {/*  <a*/}
        {/*    href="https://github.com/vercel/next.js/tree/master/examples"*/}
        {/*    className="card"*/}
        {/*  >*/}
        {/*    <h3>Examples &rarr;</h3>*/}
        {/*    <p>Discover and deploy boilerplate example Next.js projects.</p>*/}
        {/*  </a>*/}

        {/*  <a*/}
        {/*    href="https://vercel.com/import?filter=next.js&utm_source=create-next-app&utm_medium=default-template&utm_campaign=create-next-app"*/}
        {/*    className="card"*/}
        {/*  >*/}
        {/*    <h3>Deploy &rarr;</h3>*/}
        {/*    <p>*/}
        {/*      Instantly deploy your Next.js site to a public URL with Vercel.*/}
        {/*    </p>*/}
        {/*  </a>*/}
        {/*</div>*/}

        <CentralManager />

        {ids.map((item, index) => {
          return <Room key={index} id={item} />;
        })}

        <AddRoom />
      </main>

      <footer>
        <a
          href="https://vercel.com?utm_source=create-next-app&utm_medium=default-template&utm_campaign=create-next-app"
          target="_blank"
          rel="noopener noreferrer"
        >
          Powered by <img src="/vercel.svg" alt="Vercel" className="logo" />
        </a>
      </footer>
    </div>
  );
}
