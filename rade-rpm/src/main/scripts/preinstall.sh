getent group %{rade_group} >/dev/null || groupadd -r %{rade_group}
getent passwd %{rade_user} >/dev/null || /usr/sbin/useradd --comment "Rade Daemon User" --shell /bin/bash -M -r -g %{rade_group} --home %{rade_home} %{rade_user}
