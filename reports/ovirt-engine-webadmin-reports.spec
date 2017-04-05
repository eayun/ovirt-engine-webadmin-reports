%define _version 1.0
%define _release 1

Name:		ovirt-engine-webadmin-reports
Version:	%{_version}
Release:	%{_release}%{?dist}
Summary:	report portal based on ovirt-engine dwh database

Group:		ovirt-engine-third-party
License:	GPL
URL:		http://www.eayun.com
Source0:	ovirt-engine-webadmin-reports-%{_version}.tar.gz
BuildRoot:	%(mktemp -ud %{_tmppath}/%{name}-%{version}-%{release}-XXXXXX)

BuildRequires:	maven
Requires:	ovirt-engine >= 3.6.5

%description

%prep
%setup -q


%build
mvn clean package war:war


%install
rm -rf %{buildroot}
mkdir -p %{buildroot}/usr/share/ovirt-engine/ui-plugins/
mkdir -p %{buildroot}/usr/share/ovirt-engine/setup/plugins/ovirt-engine-setup/ovirt-engine-webadmin-reports-plugin/
mkdir -p %{buildroot}/usr/share/ovirt-engine-webadmin-reports/deployments
mkdir -p %{buildroot}/etc/httpd/conf.d/
mkdir -p %{buildroot}/etc/ovirt-engine-webadmin-reports/
mkdir -p %{buildroot}/usr/sbin/
mkdir -p %{buildroot}/usr/lib/systemd/system
mkdir -p %{buildroot}/var/log/ovirt-engine-webadmin-reports/
cp dist/report.json %{buildroot}/usr/share/ovirt-engine/ui-plugins/
cp -r dist/report-resources %{buildroot}/usr/share/ovirt-engine/ui-plugins/
cp -r dist/otopi/* %{buildroot}/usr/share/ovirt-engine/setup/plugins/ovirt-engine-setup/ovirt-engine-webadmin-reports-plugin/
cp dist/etc/z-ovirt-engine-webadmin-reports-proxy.conf %{buildroot}/etc/httpd/conf.d/
cp dist/etc/ovirt-engine-webadmin-reports.xml %{buildroot}/etc/ovirt-engine-webadmin-reports/
cp target/ovirt-engine-webadmin-reports.war %{buildroot}/usr/share/ovirt-engine-webadmin-reports/deployments/
cp dist/bin/ovirt-engine-webadmin-reports-setup %{buildroot}/usr/sbin/
cp dist/service/ovirt-engine-webadmin-reports.service %{buildroot}/usr/lib/systemd/system
cp dist/etc/ovirt-engine-webadmin-reports.properties %{buildroot}/etc/ovirt-engine-webadmin-reports/
touch %{buildroot}/etc/ovirt-engine-webadmin-reports/mgmt-users.properties
touch %{buildroot}/etc/ovirt-engine-webadmin-reports/mgmt-groups.properties
touch %{buildroot}/etc/ovirt-engine-webadmin-reports/application-users.properties
touch %{buildroot}/etc/ovirt-engine-webadmin-reports/application-roles.properties

%clean
rm -rf %{buildroot}


%files
%defattr(-,root,root,-)
%dir /etc/httpd/conf.d/
%config /etc/httpd/conf.d/z-ovirt-engine-webadmin-reports-proxy.conf
%attr(0755,root,root) /usr/sbin/ovirt-engine-webadmin-reports-setup
%attr(0644,root,root) /usr/lib/systemd/system/ovirt-engine-webadmin-reports.service
/usr/share/ovirt-engine/ui-plugins/
/usr/share/ovirt-engine/setup/plugins/ovirt-engine-setup/ovirt-engine-webadmin-reports-plugin/
/usr/share/ovirt-engine-webadmin-reports/
%config /etc/ovirt-engine-webadmin-reports/*
/var/log/ovirt-engine-webadmin-reports/


%changelog

* Thu May 12 2016 walteryang47 <walteryang47@gmail.com> 1.0-0
- First build

