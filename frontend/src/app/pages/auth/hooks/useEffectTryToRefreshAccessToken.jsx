import { useEffect } from "react";
import useTryToRefreshAccessToken from "./useTryToRefreshAccessToken";

function useEffectTryToRefreshAccessToken() {
  const tryToRefreshAccessToken = useTryToRefreshAccessToken();
  useEffect(() => {
    tryToRefreshAccessToken();
  }, [tryToRefreshAccessToken]);
}

export default useEffectTryToRefreshAccessToken;