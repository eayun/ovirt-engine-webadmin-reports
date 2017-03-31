"""ovirt engine webadmin reports setup."""

import os

from otopi import plugin, util

from ovirt_engine_setup import constants as osetupcons
from ovirt_engine_setup.engine import constants as oenginecons


@util.export
class Plugin(plugin.PluginBase):
    """ovirt engine webadmin reports setup."""

    @plugin.event(
        stage=plugin.Stages.STAGE_CLOSEUP,
        after=(
            osetupcons.Stages.DIALOG_TITLES_E_SUMMARY,
        ),
        condition=lambda self: (
            self.environment[oenginecons.EngineDBEnv.NEW_DATABASE]
        ),
    )
    def enable_ovirt_engine_webadmin_reports_plugin(self):
        os.system("ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime")
        # workaround for timezone setup:
        os.system("sed -i '/^timezone =/c\ timezone = 8' /var/lib/pgsql/data/postgresql.conf")
        os.system("sed -i '/^log_timezone =/c\log_timezone = 8' /var/lib/pgsql/data/postgresql.conf")
        os.system("ovirt-engine-webadmin-reports-setup --password=%s"
            % self.environment[oenginecons.ConfigEnv.ADMIN_PASSWORD])
        self.dialog.note(text="ovirt engine webadmin reports enabled.")

    @plugin.event(
        stage=plugin.Stages.STAGE_CLOSEUP,
        after=(
            osetupcons.Stages.DIALOG_TITLES_E_SUMMARY,
        ),
        condition=lambda self: (
            not self.environment[oenginecons.EngineDBEnv.NEW_DATABASE]
        ),
    )
    def restart_ovirt_engine_webadmin_reports_plugin(self):
        os.system("service ovirt-engine-webadmin-reports restart")
